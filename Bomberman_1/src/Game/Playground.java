/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import powerup.PowerUp;

/**
 *
 * @author jeffrey
 */
public class Playground{

    private TiledMap map;
    private final String path = "res" + File.separator + "map.tmx";
    //private List<Box> boxes;
    private List<IGameObject> mapobjects;
    //private List<PowerUp> powerups;
    private List<Wall> walls;
    private List<Bomb> bombs;
    private List<Bomb> bombs2;

    public Playground() {
        try {
            map = new TiledMap(path);
        } catch (SlickException ex) {
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
        }
        //boxes = new CopyOnWriteArrayList<Box>();
        mapobjects = new CopyOnWriteArrayList<IGameObject>();
        //powerups = new CopyOnWriteArrayList<PowerUp>();
        walls = new CopyOnWriteArrayList<Wall>();
        bombs = new CopyOnWriteArrayList<>();
        bombs2 = new CopyOnWriteArrayList<>();
    }

    public List<IGameObject> getMapobjects() {
        return mapobjects;
    }

    public void setMapobjects(List<IGameObject> mapobjects) {
        this.mapobjects = mapobjects;
    }

    public void addToLevel(IGameObject o) {
        mapobjects.add(o);
    }

    /**
     * removes object from level
     *
     * @param o specific instance of object
     */
    public void removeFromLevel(IGameObject o) {
        mapobjects.remove(o);
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void addWall(Wall w) {
        walls.add(w);
    }

    public TiledMap getMap() {
        return map;
    }

//    public List<Box> getBoxes() {
//        return boxes;
//    }
//
//    public void addBox(Box b) {
//        boxes.add(b);
//    }
//
//    public void RemoveBox(Box b) {
//        boxes.remove(b);
//    }
//
//    public void addPowerup(PowerUp p) {
//        powerups.add(p);
//    }
//
//    public void removePowerup(PowerUp p) {
//        powerups.remove(p);
//    }
//
//    public List<PowerUp> getPowerups() {
//        return powerups;
//    }
    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Bomb> getBombs2() {
        return bombs2;
    }

    public void addBombs1(Bomb b) {
        bombs.add(b);
    }

    public void addBombs2(Bomb b) {
        bombs2.add(b);
    }

    public void removeBombs1(Bomb b) {
        bombs.remove(b);
    }

    public void removeBombs2(Bomb b) {
        bombs2.remove(b);
    }
}
