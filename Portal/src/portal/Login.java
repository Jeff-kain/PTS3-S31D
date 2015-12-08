/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import database.UserController;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Rob
 */
public class Login extends Application {
    
    UserController uc;
    ExecutorService executor;
    boolean isOk;
    
    
    @Override
    public void start(Stage stage) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFMXL.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
                }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        executor = Executors.newFixedThreadPool(3);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
                System.out.println(isOk);
            }
        });
    }
    
}
