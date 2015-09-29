/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.Level;
import game.actor.Player;
import game.actor.enemies.Blob;
import game.actor.enemies.Broom;
import game.actor.enemies.Cron;
import game.actor.enemies.Dragon;
import game.actor.enemies.Fantom;
import game.item.BombsUp;
import game.item.KickUp;
import game.item.RangeUp;
import game.item.SpeedUp;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Michal
 */
public class Map {

    private TiledMap mapa;
    private Level level = Level.getLevel();
    private List<Walls> Walls;
    private int[][] wallMap;

    /**
     * saves instance of level to variable
     * @param level
     */
    public Map(Level level) {
        this.level = level;
    }

    /**
     * loads tiled map according to name of level |
     * declares 2-dimensional array for walls |
     * processes map
     * @param level string with name of level
     */
    public void loadMap(String level) {
        try {
            mapa = new TiledMap("resources/map/" + level + ".tmx");
            Walls = new ArrayList<Walls>();
            wallMap = new int[mapa.getWidth()][];
            for (int i = 0; i < mapa.getWidth(); i++) {
                wallMap[i] = new int[mapa.getHeight()];
            }
            processMap();
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
    }

    private void processMap() throws SlickException {
        //clears array of walls
        for (int x = 0; x < mapa.getWidth(); x++) {
            for (int y = 0; y < mapa.getHeight(); y++) {
                wallMap[x][y] = 0;
            }
        }
        // detects all objects in the tiled map, sets their position and adds them into level
        for (int i = 0; i < mapa.getObjectCount(0); i++) {
            switch (mapa.getObjectType(0, i)) {
                case "player":
                    Player hrac = new Player();
                    hrac.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(hrac);
                    level.setPlayer(hrac);
                    break;
                case "blob":
                    Blob blob = new Blob();
                    blob.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(blob);
                    break;
                case "cron":
                    Cron cron = new Cron();
                    cron.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(cron);
                    break;
                case "broom":
                    Broom broom = new Broom();
                    broom.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(broom);
                    break;
                case "fantom":
                    Fantom fantom = new Fantom();
                    fantom.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(fantom);
                    break;
                case "bomb":
                    BombsUp bomba = new BombsUp();
                    bomba.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(bomba);
                    break;
                case "speed":
                    SpeedUp speed = new SpeedUp();
                    speed.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(speed);
                    break;
                case "kick":
                    KickUp kick = new KickUp();
                    kick.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(kick);
                    break;
                case "range":
                    RangeUp range = new RangeUp();
                    range.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(range);
                    break;
                case "portal":
                    Portal portal = new Portal();
                    portal.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(portal);
                    break;
                case "dragon":
                    Dragon dragon = new Dragon(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                //    dragon.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(dragon);
                    break;
            }
        }
        // detects all walls in tiled map, sets their position and adds them 
        // into list and array of walls   
        int wallX;
        int wallY;
        for (int i = 0; i < mapa.getObjectCount(1); i++) {
            wallX = mapa.getObjectX(1, i);
            wallY = mapa.getObjectY(1, i);
            wallMap[wallX / 32][wallY / 32] = 1;
            switch (mapa.getObjectType(1, i)) {
                case "wall":
                    Wall stena = new Wall();
                    Walls.add(stena);
                    stena.setPosition(wallX, wallY);
                    break;

                case "block":
                    Block blok = new Block();
                    Walls.add(blok);
                    blok.setPosition(wallX, wallY);
                    break;
            }
        }
    }

    /**
     * gets instance of tiled map
     * @return tiled map
     */
    public TiledMap getTiledMap() {
        return this.mapa;
    }

    /**
     * gets List of Walls
     * @return List of walls
     */
    public List<Walls> getWalls() {
        return Walls;
    }

    /**
     * gets 2-dimensional array indicating position of walls
     * @return array of walls
     */
    public int[][] getWallMap() {
        return wallMap;
    }
}
