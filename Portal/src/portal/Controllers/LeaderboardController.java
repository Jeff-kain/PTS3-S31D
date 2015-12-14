/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.Controllers;

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
import java.util.ResourceBundle;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import portalserver.interfaces.ILobby;
/**
 *
 * @author Rob
 */
public class LeaderboardController implements Initializable {

    @FXML private Button btnSearch;
    @FXML private ListView<ILobby> lvwGames;
    @FXML private ListView<ILobby> lvwScores;
    @FXML private TextField tfPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    public void onSearch(Event evt) {

    }
}
