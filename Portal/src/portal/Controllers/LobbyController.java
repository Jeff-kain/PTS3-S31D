package portal.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import portal.Administration;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tverv on 15-Dec-15.
 */
public class LobbyController implements Initializable{

    @FXML Label lblLobbyName;
    @FXML Button btnStartGame;
    @FXML Button btnCancel;
    @FXML ListView<String> lvwPlayers;

    ObservableList<String> observablePlayers;

    Administration admin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        admin = Administration.getInstance();

        try {
            lblLobbyName.setText("Lobby: " + admin.getSelectedLobby().getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        observablePlayers = FXCollections.observableArrayList();
        lvwPlayers.setItems(observablePlayers);

        try {
            observablePlayers.addAll(admin.getSelectedLobby().getPlayers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        updatePlayers();
    }

    private void updatePlayers() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        System.out.println("Timer");
                        observablePlayers.clear();
                        observablePlayers.addAll(admin.getSelectedLobby().getPlayers());

                        if(observablePlayers.size() == 2) {
                            btnStartGame.setDisable(false);
                        }
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 0, 1000);
    }
}
