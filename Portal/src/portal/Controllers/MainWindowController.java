package portal.Controllers;

import javafx.beans.value.ChangeListener;
import database.DatabaseConnection;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.util.Callback;
import portal.Models.Game;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.stage.Stage;
import portal.*;
import portalserver.User;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.IPlayer;

import static portal.Portal.Stage;

/**
 * Created by tverv on 08-Dec-15.
 */
public class MainWindowController implements Initializable {
    //Observable lists
    ObservableList<Game> observableGames;
    ObservableList<String> observableLobbies;
    ObservableList<String> observablePlayers;
    HashMap<String,ILobby> lobbiesHashMap;
    @FXML private ListView<Game> lvwGames;
    @FXML private ListView<String> lvwLobbies;
    @FXML private ListView<String> lvwPlayers;
    @FXML TextField tfSend;
    @FXML Button btnSend;
    @FXML Button btnAddLobby;
    @FXML Button btnJoinLobby;
    @FXML TextArea taChat;

    String address;
    String username;
    ArrayList<String> users;
    Boolean isConnected;
    private Administration admin;
    int port;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    InetAddress addr;

    // the server, the port and the username


    public MainWindowController() {
        admin = Administration.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lobbiesHashMap = new HashMap<>();

        observableLobbies = FXCollections.observableArrayList();
        lvwLobbies.setItems(observableLobbies);
        observableGames = FXCollections.observableArrayList();
        lvwGames.setItems(observableGames);
        observablePlayers = FXCollections.observableArrayList();
        lvwPlayers.setItems(observablePlayers);

        users = new ArrayList();
        isConnected = false;
        port = 2222;
        initChat();

        try {
            observableGames.addAll(admin.getPortal().getGames(admin.getUsername(), admin.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        lvwLobbies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                btnJoinLobby.setDisable(true);
            } else {
                btnJoinLobby.setDisable(false);
            }
        });

        lvwGames.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnAddLobby.setDisable(false);
            admin.setSelectedGameID(newValue);

            try {
                observableLobbies.clear();
                List<ILobby> lobbies = admin.getPortal().getLobbies(admin.getUsername(), admin.getPassword(), newValue);

                for(ILobby lobby: lobbies) {
                    System.out.println(lobby.getName());
                    observableLobbies.add(lobby.getName());
                    lobbiesHashMap.put(lobby.getName(), lobby);
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public void addLobby(Event evt) {
        showAddLobbyWindow();
    }

    public void initChat() {
        taChat.setEditable(false);
        username = Administration.getInstance().getUsername();
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
    
    public void onStart(Event evt) {
        
    }
    
    public void onRefresh(Event evt) {
        
    }
        
    public void onLeaderboard(Event evt) {
        showLeaderboardWindow();
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

    public void joinLobby(Event evt) {
        ILobby lobby = lobbiesHashMap.get(lvwLobbies.getSelectionModel().getSelectedItem());
        try {
            IPlayer player = lobby.joinGame(admin.getUsername(), admin.getPassword());

            List<String> bla = player.getPlayers();

            for (String b: bla) {
                System.out.println(b);
            }

            lvwPlayers.setItems(observablePlayers);
            observablePlayers.addAll(player.getPlayers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public void onExit(Event evt) {
        userDisconnect();
        admin.setUsername("Null");
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

                username = admin.getUsername();

                try {

                    //84.26.129.94
                    address = "127.0.0.1";
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

    private void showAddLobbyWindow() {
        //Loading the .fxml file.
        Stage stage = new Stage();

        Parent root = null;
        try {

            System.out.println("Pad: " + getClass().getResource("AddLobby.fxml"));
            root = FXMLLoader.load(getClass().getResource("AddLobby.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (root != null) {
            Scene scene = new Scene(root, 300, 400);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Portal.Stage);
            stage.setTitle("Add lobby");
            stage.setScene(scene);
            stage.show();
            System.out.println("Foo");

        } else {
            System.out.println("Failed");
        }
    }
    
    private void showLeaderboardWindow() {
        //Loading the .fxml file.
        Stage stage = new Stage();
        Parent root = null;
        try {

            System.out.println("Pad: " + getClass().getResource("Leaderboard.fxml"));
            root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (root != null) {
            Scene scene = new Scene(root, 300, 400);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Portal.Stage);
            stage.setTitle("Leaderboard");
            stage.setScene(scene);
            stage.show();
            System.out.println("Foo");

        } else {
            System.out.println("Failed");
        }
    }
}