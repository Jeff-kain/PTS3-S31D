/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.File;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author jeffrey
 */
public class Playground {
    private TiledMap map;
    private final String path = "res" + File.separator + "map.tmx";

    public Playground() throws SlickException {
       map = new TiledMap(path);
    }
    
    public TiledMap getMap() {
        return map;
    }
    
    
}
