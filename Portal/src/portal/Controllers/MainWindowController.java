package portal.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import portal.Administration;
import portal.Models.Game;
import portal.Portal;
import portalserver.interfaces.ILobby;
import portalserver.interfaces.IPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

import static portal.Portal.Stage;

/**
 * Created by tverv on 08-Dec-15.
 */
public class MainWindowController implements Initializable {

    //Observable lists
    ObservableList<Game> observableGames;
    ObservableList<String> observableLobbies;
    ObservableList<String> observablePlayers;
    HashMap<String, ILobby> lobbiesHashMap;
    @FXML
    private ListView<Game> lvwGames;
    @FXML
    private ListView<String> lvwLobbies;
    @FXML
    TextField tfSend;
    @FXML
    Button btnSend;
    @FXML
    Button btnAddLobby;
    @FXML
    Button btnJoinLobby;
    @FXML
    TextArea taChat;

    private String address, username;
    private ArrayList<String> users;
    private Boolean isConnected;
    private Administration admin;
    private Game selectedGame;
    private String selectedLobbyName;

    private int port;
    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;
    private InetAddress addr;

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
                admin.setSelectedLobby(lobbiesHashMap.get(newValue));
                selectedLobbyName = newValue;
                btnJoinLobby.setDisable(false);
            }
        });

        lvwGames.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedGame = newValue;
            loadLobbies(newValue);
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
        } catch (Exception e) {
        }
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

        if (root != null) {
            Scene scene = new Scene(root, 300, 400);
            Stage.setTitle("Login");
            Stage.setScene(scene);
            Stage.show();
        } else {
            System.out.println("Failed");
        }
    }

    public void onSettings(Event evt) {

    }

    public void onRefresh(Event evt) {
        if (selectedGame != null) {
            loadLobbies(selectedGame);
        }
    }

    public void loadLobbies(Game newValue) {
        btnAddLobby.setDisable(false);
        admin.setSelectedGameID(newValue);

        try {
            observableLobbies.clear();
            List<ILobby> lobbies = admin.getPortal().getLobbies(admin.getUsername(), admin.getPassword(), newValue);

            for (ILobby lobby : lobbies) {
                System.out.println(lobby.getName());
                observableLobbies.add(lobby.getName());
                lobbiesHashMap.put(lobby.getName(), lobby);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onLeaderboard(Event evt) {
        showLeaderboardWindow();
    }

    public void playOffline() throws InterruptedException, UnknownHostException {

//        Runtime.getRuntime().wait();
//        Process p;
//        try {
//            p = Runtime.getRuntime().exec("java -jar Bomberman_1.jar localgame 192");
//        } catch (Exception e) {
//        }
////            ProcessBuilder pb = new ProcessBuilder("java -jar Bomberman_1.jar");
////            Process p1 = pb.start();
////        } catch (IOException ex) {
////            Logger.getLogger(JavaApplication10.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        Thread startLocal = new Thread(new JavaApplication10());
//        startLocal.start();
        //String argss[] = {"java", "-jar", "Bomberman_1.jar", "host", InetAddress.getLocalHost().getHostAddress()};
        String argss[] = {"java", "-jar", "Bomberman_1.jar", "localgame", InetAddress.getLocalHost().getHostAddress(), Administration.getInstance().getUsername()};
        ProcessBuilder builder = new ProcessBuilder(argss).inheritIO();
        try {
            final Process process = builder.start();

            // qq.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public void joinLobby(Event evt) {
        ILobby lobby = lobbiesHashMap.get(lvwLobbies.getSelectionModel().getSelectedItem());
        try {
            IPlayer player = lobby.joinGame(admin.getUsername(), admin.getPassword());

            List<String> bla = player.getPlayers();

            for (String b : bla) {
                System.out.println(b);
            }

            showLobbiesWindow();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onExit(Event evt) {
        userDisconnect();
        admin.setUsername(null);
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
        } catch (Exception e) {
        }
    }

    public void Connect() {
        try {
            if (isConnected == false) {

                username = admin.getUsername();

                try {
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
        } catch (Exception e) {
        }
    }

    public void ListenThread() {
        try {
            Thread IncomingReader = new Thread(new IncomingReader());
            IncomingReader.start();
        } catch (Exception e) {
        }
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
        } catch (Exception e) {
        }
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

    private static class localgame implements Runnable {

        @Override
        public void run() {
            String argss[] = {"java", "-jar", "Bomberman_1.jar", "localgame", "192"};
            ProcessBuilder builder = new ProcessBuilder(argss);
            try {
                builder.redirectErrorStream(true);
                Process qq = builder.start();
                qq.waitFor();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            stage.showAndWait();
            loadLobbies(selectedGame);
            showLobbiesWindow();

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
            Scene scene = new Scene(root, 500, 400);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Portal.Stage);
            stage.setTitle("Leaderboard");
            stage.setScene(scene);
            stage.show();

        } else {
            System.out.println("Failed");
        }
    }

    private void showLobbiesWindow() {
        Stage stage = new Stage();
        Parent root = null;

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Closed!");
                try {
                    admin.getSelectedLobby().leaveGame(admin.getUsername(), admin.getPassword());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        try {

            System.out.println("Pad: " + getClass().getResource("Lobby.fxml"));
            root = FXMLLoader.load(getClass().getResource("Lobby.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (root != null) {
            Scene scene = new Scene(root, 300, 400);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Portal.Stage);
            stage.setTitle(selectedLobbyName);
            stage.setScene(scene);
            stage.show();

        } else {
            System.out.println("Failed");
        }
    }
}
