/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author tverv
 */
public class Portal extends Application {

    DatabaseConnection uc;
    ExecutorService executor;
    boolean isOk;

    public static Stage Stage;

    @Override
    public void start(Stage primaryStage) {
        Stage = primaryStage;

        //Loading the .fxml file.
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Views/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 300, 400);

        Stage.setTitle("Login");
        Stage.setScene(scene);
        Stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
