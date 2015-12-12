package portal.Controllers;

import database.DatabaseConnection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import portal.Portal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.JavaFXBuilderFactory;

/**
 * Created by tverv on 08-Dec-15.
 */
public class LoginController implements Initializable {
    //Controls
    @FXML private Button btnLogin;
    @FXML private TextField tfdUsername;
    @FXML private TextField pfdPassword;

    /**
     * Initializes the controller class.
     */
    private Stage stage;
    DatabaseConnection dc;
    ExecutorService executor;
    boolean isOk;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            dc = DatabaseConnection.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        executor = Executors.newFixedThreadPool(3);
    }

    public void btnLogin(Event evt) {
        try {
            Boolean result = dc.CheckLogin(tfdUsername.getText(),pfdPassword.getText());

            if(result) {
                loadMainWindow();
            } else {
                System.out.println("login failed");
            }
        }

        catch (SQLException ex) {
            ex.printStackTrace();
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
        Stage stage = Portal.Stage;

        Parent root = null;
        try {

            //Commit Merge
//            root = FXMLLoader.load(getClass().getResource("../Views/MainWindow.fxml"));
//        } catch (Exception e) {

            System.out.println("Pad: " + getClass().getResource("MainWindow.fxml"));
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (root != null) {
            Scene scene = new Scene(root, 800, 480);

            stage.setTitle("Portal");
            stage.setScene(scene);
            stage.show();

        } else {
            System.out.println("Failed");
        }
    }
}
