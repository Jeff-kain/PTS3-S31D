///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Chat;
//
//import bomberman.Bomberman;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.event.ActionEvent;
//import javafx.event.Event;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.stage.Modality;
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import org.newdawn.slick.AppGameContainer;
//import org.newdawn.slick.SlickException;
//
///**
// * FXML Controller class
// *
// * @author Rob
// */
//public class GameFXMLController implements Initializable {
//
//    /**
//     * Initializes the controller class.
//     */
//    
//    @FXML TextField tfPlayer1,tfPlayer2;
//    @FXML Button btnConnect, btnDisconnect;
//    
//    public static int Name;
//    boolean isStarted = false;
//    public int x;
//    public int y;
//    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//        Name = 0;
//        x = 460;
//        y = 100;
//    }    
//    
//    public void btConnect (Event evt) {
//        if(!isStarted) {
//            isStarted = true;
//            for(int i = 1; i <= 2; i++) {
//                try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientGUI.fxml"));
//                Parent root = (Parent) fxmlLoader.load();
//                Stage stage = new Stage();
//                
//                Scene scene = new Scene(root);
//                stage.setTitle("Chat");
//                stage.setScene(scene);
//                
//                y += 300;
//                
//                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//                stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - x);
//                stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - y);
//                
//                stage.show();
//                stage.setResizable(false);
//                } catch(Exception e) {
//                   System.out.println(e.getMessage());
//                }
//            } 
//            try {
//                AppGameContainer appgc;
//                appgc = new AppGameContainer(new Bomberman("Bomberman"));
//                appgc.setDisplayMode(720, 720, false);
//                appgc.setVSync(true);
//                appgc.start();
//            } catch (SlickException ex) {
//                Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        isStarted = false;
//        x = 460;
//        y = 100;
//    }
//     
//    public void btDisconnect (Event evt) {
//    }
//}
