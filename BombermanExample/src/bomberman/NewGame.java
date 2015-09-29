/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class NewGame {
     
    /**
     * runs the windows application
     * @throws SlickException  
     */
    public NewGame() throws SlickException{
        AppGameContainer app;
        app = new AppGameContainer(new Game());
        app.setDisplayMode(640, 480, false);
        app.setVSync(true);
        app.start();
        
    }
    
}
