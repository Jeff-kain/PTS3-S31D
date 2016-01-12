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

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by tverv on 13-Dec-15.
 */
public class AddLobbyController implements Initializable{

    @FXML private Label lblGame;
    @FXML private TextField tfdName;
    @FXML private TextField tfdPassword;

    @FXML private Button btnSave;

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

                if(newValue.equals("")) {
                    btnSave.setDisable(true);
                } else {
                    btnSave.setDisable(false);
                }
            }
        });

    }

    public void saveLobby(Event evt) {
        if(tfdName.getText() != "") {
            try {
                IHost hostedLobby = portal.createLobby(admin.getUsername(),admin.getPassword(), admin.getSelectedGame(), tfdName.getText(), tfdPassword.getText());
                admin.setHostedLobby(hostedLobby);
                ((Stage)lblGame.getScene().getWindow()).close();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
