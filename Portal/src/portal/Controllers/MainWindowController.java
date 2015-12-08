package portal.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import portal.Models.Game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tverv on 08-Dec-15.
 */
public class MainWindowController implements Initializable {
    //Observable lists
    ObservableList<Game> observableGames;

    @FXML
    private ListView<Game> lvwGame;

    public MainWindowController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Game bomberman = new Game("Bomberman", "Bla");

        observableGames = FXCollections.observableArrayList();
        observableGames.add(bomberman);

        lvwGame.setItems(observableGames);
    }
}
