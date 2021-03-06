package portal.Controllers;

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
import portalserver.interfaces.IHost;
import portalserver.interfaces.IPortal;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by tverv on 13-Dec-15.
 */
public class AddLobbyController implements Initializable {

    @FXML
    private Label lblGame;
    @FXML
    private TextField tfdName;

    @FXML
    private Button btnSave;

    private Administration admin;
    private IPortal portal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        admin = Administration.getInstance();
        portal = admin.getPortal();

        lblGame.setText(admin.getSelectedGame().getName());

        tfdName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue.equals("")) {
                    btnSave.setDisable(true);
                } else {
                    btnSave.setDisable(false);
                }
            }
        });

        tfdName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    createLobby();
                }
            }
        });
    }

    public void saveLobby(Event evt) {
        createLobby();
    }

    private void createLobby() {
        if (tfdName.getText() != "") {
            try {
                IHost hostedLobby = portal.createLobby(admin.getUsername(), admin.getPassword(), admin.getSelectedGame(), tfdName.getText(), "", InetAddress.getLocalHost().getHostAddress());
                admin.setHostedLobby(hostedLobby);
                ((Stage) lblGame.getScene().getWindow()).close();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
