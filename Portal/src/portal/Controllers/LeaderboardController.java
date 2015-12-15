/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.Controllers;

import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import portal.Administration;
import portalserver.interfaces.IPortal;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import portal.Models.Game;
import portal.Models.Score;
import portalserver.interfaces.ILobby;
/**
 *
 * @author Rob
 */
public class LeaderboardController implements Initializable {

    @FXML private Button btnSearch;
    @FXML private ListView<Game> lvwGames;
    @FXML private ListView<Score> lvwScores;
    @FXML private TextField tfPlayer;
    
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
        
//        new java.util.Timer().schedule( 
//        new java.util.TimerTask() {
//            @Override
//            public void run() {
//                UpdateScores();
//            }}, 30000);
    }
    
    public void onSearch(Event evt) {
        try {
            observableScores.clear();
            Score PlayerScore = admin.getPortal().getScoresPlayer(admin.getUsername(), admin.getPassword(), tfPlayer.getText(), admin.getSelectedGameLeaderboard().getName());
            if(PlayerScore != null) {
                observableScores.add(PlayerScore);
            } 
            else {
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

            for(Score s: leaderboard) {
                observableScores.add(s);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
}
