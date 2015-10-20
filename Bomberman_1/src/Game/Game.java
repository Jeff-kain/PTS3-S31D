/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;

/**
 *
 * @author jeffrey
 */
public class Game {
    
    private String naam;
    private String Beschrijving;
    private static Playground playground = new Playground(); 
    
    public Game() {

    }
    public Playground playground()
    {
        return playground;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }
}
