package portal.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import portal.Models.Game;

/**
 * Created by tverv on 08-Dec-15.
 */
public class MainWindowController {
    //Observable lists
    ObservableList<Game> observableGames;

    @FXML
    private ListView<Game> lvwGame;

    public MainWindowController() {
        Game bomberman = new Game("Bomberman", "Bla");

        observableGames = FXCollections.observableArrayList();
        observableGames.add(bomberman);

        //lvwGame.setItems(observableGames);
    }
}
