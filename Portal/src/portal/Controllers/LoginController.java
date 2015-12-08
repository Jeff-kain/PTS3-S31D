package portal.Controllers;

import database.UserController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import portal.Portal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * Created by tverv on 08-Dec-15.
 */
public class LoginController implements Initializable {
    /**
     * Initializes the controller class.
     */
    UserController uc;
    ExecutorService executor;
    boolean isOk;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            uc = new UserController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        executor = Executors.newFixedThreadPool(3);
    }

    public void btnLogin(Event evt) {
        System.out.println("Foo");
        LoginAttempt();
    }

    public void LoginAttempt() {

        Callable dbQuery = new Callable() {

            @Override
            public Object call() throws Exception {
                boolean isConnected = uc.TestConnection();
                return isConnected;
            }
        };

        Future future = executor.submit(dbQuery);
        executor.submit(new Runnable(){

            @Override
            public void run() {
                try {
                    isOk = (boolean) future.get();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                }

                if(isOk) {
                    loadMainWindow();
                }
            }
        });
    }

    private void loadMainWindow() {
        //Loading the .fxml file.
        Stage primaryStage = Portal.Stage;
        System.out.println(primaryStage.getTitle());

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 800, 480);

        primaryStage.setTitle("Portal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
