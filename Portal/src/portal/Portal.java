/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import portal.Models.Game;

import java.io.IOException;
import java.util.Observable;

/**
 *
 * @author tverv
 */
public class Portal extends Application {

    //Observable lists
    ObservableList<Game> observableGames;

    @FXML
    private ListView<Game> lvwGame;

    @Override
    public void start(Stage primaryStage) {

        //Loading the .fxml file.
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

        observableGames = FXCollections.observableArrayList();
        Game game = new Game("Bomberman", "c:\\games\\bomberman");

        observableGames.add(game);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
