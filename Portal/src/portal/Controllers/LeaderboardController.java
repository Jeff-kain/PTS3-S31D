/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import portal.Administration;
import portal.Models.Game;
import portal.Models.Score;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Rob
 */
public class LeaderboardController implements Initializable {

    @FXML
    private ListView<Game> lvwGames;
    @FXML
    private ListView<Score> lvwScores;
    @FXML
    private TextField tfPlayer;

    private Administration admin;
    private ObservableList<Score> observableScores;
    private ObservableList<Game> observableGames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        admin = Administration.getInstance();

        observableScores = FXCollections.observableArrayList();
        lvwScores.setItems(observableScores);
        observableGames = FXCollections.observableArrayList();
        lvwGames.setItems(observableGames);

        try {
            observableGames.addAll(admin.getPortal().getGames(admin.getUsername(), admin.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lvwGames.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            admin.setSelectedGameLeaderboardID(newValue);
            UpdateScores();
        });

        tfPlayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    search();
                }
            }
        });
    }

    public void onSearch(Event evt) {
        search();
    }

    private void search() {
        try {
            observableScores.clear();
            Score PlayerScore = admin.getPortal().getScoresPlayer(admin.getUsername(), admin.getPassword(), tfPlayer.getText(), admin.getSelectedGameLeaderboard().getName());
            if (PlayerScore != null) {
                observableScores.add(PlayerScore);
            } else {
                System.out.println("No player or record found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void UpdateScores() {
        try {
            observableScores.clear();
            List<Score> leaderboard = admin.getPortal().getLeaderboard(admin.getUsername(), admin.getPassword(), admin.getSelectedGameLeaderboard().getName());

            for (Score s : leaderboard) {
                observableScores.add(s);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
