package portal.Controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import portal.Administration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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
    Boolean host = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        admin = Administration.getInstance();

        try {
            if(admin.getHostedLobby() != null) {
                lblLobbyName.setText("Lobby: " + admin.getHostedLobby().getName());
                host = true;
            }

            else {
                lblLobbyName.setText("Lobby: " + admin.getSelectedLobby().getName());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        observablePlayers = FXCollections.observableArrayList();
        lvwPlayers.setItems(observablePlayers);

        try {
            if(admin.getSelectedLobby() == null) {
                observablePlayers.addAll(admin.getHostedLobby().getPlayers());
            } else {
                observablePlayers.addAll(admin.getSelectedLobby().getPlayers());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        updatePlayers();
    }

    private void updatePlayers() {
        Timer timer = new Timer();
        Boolean started = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {


                    try {
                        observablePlayers.clear();

                        if(host) {
                            observablePlayers.addAll(admin.getHostedLobby().getPlayers());

                            if(observablePlayers.size() == 2) {
                                btnStartGame.setDisable(false);
                            }

                        } else {
                            observablePlayers.addAll(admin.getSelectedLobby().getPlayers());

                            if(!started) {
                                if (admin.getSelectedLobby().getGameStarted()) {
                                    System.out.println("Started");
                                    launchExecutable();
                                    timer.cancel();
                                }
                            }
                        }
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 0, 1000);
    }

    public void startGame(Event evt) {
        try {

            if(admin.getHostedLobby().startGame()) {
                System.out.println("Starting game...");
                launchExecutable();

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void launchExecutable() {

        String mode = "";
        String hostIp = "";
        String username = admin.getUsername();
        if(host) {
            try {
                mode = "host";

                hostIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Host IP is unknown. \nTry again later.");
                alert.showAndWait();
            }
        } else {
            mode = "client";
            try {
                hostIp = admin.getSelectedLobby().getHostIp();
            } catch (RemoteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Host IP is unknown. \nTry again later.");
                alert.showAndWait();
            }
        }

        String argss[] = {"java", "-jar", "Bomberman_1.jar", mode, hostIp, username};
        ProcessBuilder builder = new ProcessBuilder(argss).inheritIO();
        try {
            final Process process = builder.start();

            // qq.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }
}
