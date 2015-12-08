package portal.Controllers;

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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    }
    
    public void btSend(Event event) {
        
    }
}
