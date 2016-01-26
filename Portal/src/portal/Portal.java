/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import database.DatabaseConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author tverv
 */
public class Portal extends Application {

    DatabaseConnection uc;
    ExecutorService executor;
    boolean isOk;
    private Properties props;

    public static Stage Stage;
    public static String portalserver;

    @Override
    public void start(Stage primaryStage) {
        Stage = primaryStage;
        loadServerProps();
       //saveServerProps();
        System.out.println("props loaded");
        //Loading the .fxml file.
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Controllers/Login.fxml"));
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

    public void loadServerProps() {
        props = new Properties();
        InputStream input = null;
        File file = new File("portalserver.properties");

        try {
            input = new FileInputStream(file);

            // load a properties file
            props.load(input);

            portalserver = props.getProperty("portalserver");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveServerProps() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("portalserver.properties");

            // set the properties value
            prop.setProperty("portalserver", "192.168.1.100");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
