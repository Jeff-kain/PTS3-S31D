package portal.Controllers;

import database.DatabaseConnection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import portal.Administration;
import portal.Models.RMI.RMIClient;
import portal.Portal;
import portalserver.interfaces.ILogin;
import portalserver.interfaces.IPortal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by tverv on 08-Dec-15.ó
 */
public class LoginController implements Initializable {

    //Controls
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField tfdUsername;
    @FXML
    private TextField pfdPassword;
    @FXML
    private Label lblError;

    /**
     * Initializes the controller class.
     */
    public static Stage stage;
    DatabaseConnection dc;
    boolean isOk;
    private Administration admin;

    //RMI Stuff
    private RMIClient rmiClient;
    private ILogin login;
    private IPortal portal;

    public LoginController() {
        admin = Administration.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            dc = DatabaseConnection.getInstance();
            rmiClient = new RMIClient();
            login = rmiClient.setUp();

            if (login == null) {
                lblError.setText("No connection with the server, try again later.");
                lblError.setVisible(true);
                tfdUsername.setDisable(true);
                pfdPassword.setDisable(true);
                btnLogin.setDisable(true);
                btnRegister.setDisable(true);
            }

            admin.setLogin(login);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        pfdPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    loginCheck();
                }
            }
        });

    }

    public void btnLogin(Event evt) {
        loginCheck();
    }

    private void loginCheck() {
        try {
            if (tfdUsername.getText().equals("") || pfdPassword.getText().equals("")) {
                lblError.setText("Please fill in both fields.");
            } else {
                portal = login.login(tfdUsername.getText(), pfdPassword.getText());

                if (portal != null) {
                    admin.setPortal(portal);
                    admin.setUsername(tfdUsername.getText());
                    admin.setPassword(pfdPassword.getText());

                    loadMainWindow();
                } else {
                    lblError.setText("Invalid username or password, try again.");
                    lblError.setVisible(true);
                    System.out.println("Login failed");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRegister(Event evt) {
        try {
            if (tfdUsername.getText().equals("") || pfdPassword.getText().equals("")) {
                lblError.setText("Please fill in both fields.");
                lblError.setVisible(true);
            }
            System.out.println(tfdUsername.getText().length());
            if (tfdUsername.getText().length() < 2 || tfdUsername.getText().length() > 13 && pfdPassword.getText().length() < 2 || pfdPassword.getText().length() > 13) {
                lblError.setText("Your username is already in use!");
                lblError.setVisible(true);
            } else {
                portal = login.Register(tfdUsername.getText(), pfdPassword.getText());
                if (portal != null) {
                    admin.setPortal(portal);
                    admin.setUsername(tfdUsername.getText());
                    admin.setPassword(pfdPassword.getText());
                    loadMainWindow();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadMainWindow() throws Exception {
        //Loading the .fxml file.
        stage = Portal.Stage;

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (root != null) {
            Scene scene = new Scene(root, 800, 480);

            stage.setTitle("Portal");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } else {
            System.out.println("Failed load MainWindow");
        }
    }
}
