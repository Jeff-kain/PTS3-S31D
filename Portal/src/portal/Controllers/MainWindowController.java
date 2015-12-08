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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.Event;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import portal.*;
import portal.Models.User;

/**
 * Created by tverv on 08-Dec-15.
 */
public class MainWindowController implements Initializable {
    //Observable lists
    ObservableList<Game> observableGames;

    @FXML private ListView<Game> lvwGame;
    @FXML private TextArea txaMessages;
    @FXML private TextField tfdMessage;
    @FXML private Button btnSend;

    // the server, the port and the username
    private String server, username;
    private int port;
    
    private Client client;
        

    public MainWindowController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection dc = DatabaseConnection.getInstance();

            observableGames = dc.getGames();
            lvwGame.setItems(observableGames);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        initChat();
    }
    
    public void initChat() {
        server = "Localhost";
        port = 1500;
        username = new User().getName();
        client = new Client(server, port, username, txaMessages);
    }
    
    public void btSend(Event event) {
        
    }
    
    /*
     * To send a message to the console or the GUI
     */
    public void display(String input) {
        txaMessages.appendText(input + "\n");
    }
}
