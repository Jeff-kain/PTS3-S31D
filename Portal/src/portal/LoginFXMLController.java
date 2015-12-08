/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import database.UserController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Rob
 */
public class LoginFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    UserController uc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            uc = new UserController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void btLogin(Event evt) {
         uc.TestConnection();
    }  
    
}
