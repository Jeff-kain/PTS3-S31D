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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tverv on 08-Dec-15.
 */
public class LoginController implements Initializable {
    //Controls
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    @FXML private TextField tfdUsername;
    @FXML private TextField pfdPassword;
    @FXML private Label lblError;

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
            if(login == null) {
                lblError.setText("Connection with the server failed, try again later.");

                lblError.setVisible(true);
                btnLogin.setDisable(true);
                btnRegister.setDisable(true);
            }
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
                lblError.setText("Invalid username or password, try again.");
                lblError.setVisible(true);
                System.out.println("Login failed");
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoginAttempt() {

        Callable dbQuery = new Callable() {

            @Override
            public Object call() throws Exception {
                boolean isConnected = dc.CheckLogin(tfdUsername.getText(),pfdPassword.getText());
                return isConnected;
            }
        };

        Future future = executor.submit(dbQuery);
        executor.submit(new Runnable(){

            @Override
            public void run() {
                try {
                    isOk = (boolean) future.get();
                    
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }
        });
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
