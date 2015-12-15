package portal.Controllers;

import database.DatabaseConnection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

/**
 * Created by tverv on 08-Dec-15.ó
 */
public class LoginController implements Initializable {
    //Controls
    @FXML private TextField tfdUsername;
    @FXML private TextField pfdPassword;

    /**
     * Initializes the controller class.
     */
    public static Stage stage;
    DatabaseConnection dc;
    ExecutorService executor;
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
            admin.setLogin(login);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        executor = Executors.newFixedThreadPool(3);
    }

    public void btnLogin(Event evt) {
        try {
            portal = login.login(tfdUsername.getText(),pfdPassword.getText());

            if(portal != null) {
                admin.setPortal(portal);
                admin.setUsername(tfdUsername.getText());
                admin.setPassword(pfdPassword.getText());

                loadMainWindow();
            } else {
                System.out.println("Login failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRegister(Event evt) {
        try {
            portal = login.Register(tfdUsername.getText(),pfdPassword.getText());

            if(portal != null) {
                admin.setPortal(portal);
                admin.setUsername(tfdUsername.getText());
                admin.setPassword(pfdPassword.getText());

                loadMainWindow();
            } else {
                System.out.println("Register failed");
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
