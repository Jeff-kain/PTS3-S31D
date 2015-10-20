/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
/**
 *
 * @author jeffrey
 */
public class Playground {
    private TiledMap map;
    private final String path = "res" + File.separator + "map.tmx";
    private ArrayList<Box> boxes;


    public Playground()  {
        try {
            map = new TiledMap(path);
        } catch (SlickException ex) {
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
        }
       boxes = new ArrayList<Box>();
    }
    
    public TiledMap getMap() {
        return map;
    }
    
    public List<Box> getBoxes() {
        return boxes;
    }
    
    public void addBox(Box b)
    {
        boxes.add(b);
    }
}
