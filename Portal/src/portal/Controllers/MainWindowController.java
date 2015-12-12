package portal.Controllers;

import chat.ChatMessage;
import chat.Client;
import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import portal.Models.Game;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import portal.*;
import portal.Models.User;
import static portal.Portal.Stage;

/**
 * Created by tverv on 08-Dec-15.
 */
public class MainWindowController implements Initializable {
    //Observable lists
    ObservableList<Game> observableGames;
    @FXML private ListView<Game> lvwGame;
    @FXML TextField tfSend;
    @FXML Button btnSend;
    @FXML TextArea taChat;

    String address;
    String username;
    ArrayList<String> users;
    Boolean isConnected;
    int port;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    InetAddress addr;

    // the server, the port and the username


    public MainWindowController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        users = new ArrayList();
        isConnected = false;
        port = 2222;
        initChat();

        try {
            DatabaseConnection dc = DatabaseConnection.getInstance();

            observableGames = dc.getGames();
            lvwGame.setItems(observableGames);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initChat() {
        taChat.setEditable(false);
        username = new User().getName();
        Connect();
    }

    public void Display(String Input) {
        try {
            taChat.appendText(Input);
        } catch(Exception e) {}
    }

    public void EndChat() {
        taChat.end();
    }
    
    public void onChangeUser(Event evt) {
        userDisconnect();
        Stage stage = LoginController.stage;
        Parent root = null;
        try {       
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        if(root != null) {
            Scene scene = new Scene(root, 300, 400);
            Stage.setTitle("Login");
            Stage.setScene(scene);
            Stage.show();
        }
        else {
            System.out.println("Failed");
        }
    }

    public void onSettings(Event evt) {
        
    }
    
    public void playOffline(Event evt) {
        Process p;
        try {
            p = Runtime.getRuntime().exec("\"C:/Program Files (x86)/Gyazo/Gyazowin.exe\"");
        p.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onExit(Event evt) {
        userDisconnect();
        new User().setName("Null");
        Stage stage = LoginController.stage;
        stage.setOnCloseRequest(e -> Platform.exit());
        System.exit(0);
        
    }
    
    public void btSend(Event evt) {
        try {
            if ((tfSend.getText()).equals("")) {
                tfSend.setText("");
                tfSend.requestFocus();
            } else {
                try {
                    writer.println(username + ":" + tfSend.getText() + ":" + "Chat");
                    writer.flush();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Display("Message was not sent. \n");
                }
                tfSend.setText("");
                tfSend.requestFocus();
            }
        } catch(Exception e) {}
    }

    public void Connect() {
        try {
            if (isConnected == false) {

                username = new User().getName();

                try {

                    //84.26.129.94
                    address = "192.168.178.105";
                    sock = new Socket(address, port);
                    InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ":has connected.:Connect");
                    writer.flush();

                    isConnected = true;

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    Display("Cannot Connect! Try Again. \n");
                }

                ListenThread();

            } else if (isConnected == true) {
                Display("You are already connected. \n");
            }
        } catch (Exception e) {
        }
    }

    public void userDisconnect() {
        try {
            sendDisconnect();
            Disconnect();
            taChat.setText("");
        } catch(Exception e) {}
    }

    public void ListenThread() {
        try {
            Thread IncomingReader = new Thread(new IncomingReader());
            IncomingReader.start();
        } catch(Exception e) {}
    }

    public void userAddClient(String data) {
        users.add(data);
    }

    public void userRemoveClient(String data) {
        Display(data + " is now offline.\n");
    }

    public void writeUsers() {
        try {
            String[] tempList = new String[(users.size())];
            users.toArray(tempList);
            for (String token : tempList) {
                Display(token + "\n");
            }
        } catch(Exception e) {}
    }

    public void sendDisconnect() {
        try {
            writer.println(username + ": :Disconnect");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Display("Could not send Disconnect message.\n");
        }
    }

    public void Disconnect() {
        try {
            Display("Disconnected.\n");
            sock.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Display("Failed to disconnect. \n");
        }
        isConnected = false;
    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");

                    if (data[2].equals(chat)) {
                        Display(data[0] + ": " + data[1] + "\n");
                        EndChat();

                    } else if (data[2].equals(connect)) {

                        userAddClient(data[0]);

                    } else if (data[2].equals(disconnect)) {
                        userRemoveClient(data[0]);

                    } else if (data[2].equals(done)) {
                        writeUsers();
                        users.clear();
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
