/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import bomberman.Game;
import bomberman.Game.GameState;
import game.actor.Player;
import game.map.Map;
import game.map.Wall;
import game.map.Walls;
import game.score.BestScore;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michal
 */
public class Level {
    private ArrayList<MapObjects> listOfObjects;
    private static Level instance = new Level();
    private Player player;
    private Map mapa = new Map(this);
    private GameState gameState;
    private BestScore bestScore;
    private int levelNumber;

    // creates List of all objects in map
    // creates new score for current game
    // sets first level for new game
    private Level() {
        listOfObjects = new ArrayList<MapObjects>();
        bestScore = new BestScore();
        levelNumber = 1;
    }

    /**
     *  gets instance of level
     * @return instance of Level
     */
    public static Level getLevel() {
        return instance;
    }

    /**
     * loads map defined by specific path | sets game state on PLAYING
     * @param level specifics name of map to be loaded
     */
    public void loadLevel(String level) {
        mapa.loadMap(level);
        this.gameState=GameState.PLAYING;
    }

    /**
     * renders basic tiledmap background and blocks
     */
    public void show() {
        mapa.getTiledMap().render(0, 0);
    }

    /**
     * renders all walls from the list
     */
    public void showWalls() {
        for (Walls w : mapa.getWalls()) {
            if (w instanceof Wall) {
                w.getAnimation().draw(w.getX(), w.getY());
            }
        }
    }

    /**
     * gets instance of player in current level
     * @return instance of player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * sets player in current level
     * @param player instance of player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * adds object from map to level
     * @param o specific instance of object
     */
    public void addToLevel(MapObjects o) {
        listOfObjects.add(o);
    }

    /**
     * removes object from level
     * @param o specific instance of object
     */
    public void removeFromLevel(MapObjects o) {
        listOfObjects.remove(o);
    }

    /**
     * gets List of all objects in level
     * @return List of objects
     */
    public List<MapObjects> getListOfObjects() {
        return listOfObjects;
    }

    /**
     * gets instance of map
     * @return map
     */
    public Map getMap() {
        return mapa;
    }
    
    /**
     * reloads level after finishing | clears all objects in the List
     */
    public void reloadLevel(){
        listOfObjects.clear();
    }

    /**
     * @return the gameState
     */
    public Game.GameState getGameState() {
        return gameState;
    }

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the bestScore
     */
    public BestScore getBestScore() {
        return bestScore;
    }

    /**
     * @return the number of current level
     */
    public int getLevelNumber() {
        return levelNumber;
    }
    
    /**
     * increments number of level
     */
    public void incLevel() {
        this.levelNumber++;
    }

}
