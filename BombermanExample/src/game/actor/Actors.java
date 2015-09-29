/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Level;
import game.MapObjects;
import game.map.Walls;

/**
 *
 * @author Michal
 */
public abstract class Actors extends MapObjects {

    /**
     * enumerated type for direction
     */
    public enum Direction{
        /**
         *
         */
        NORTH,
        /**
         *
         */
        WEST,
        /**
         *
         */
        EAST, 
        /**
         *
         */
        SOUTH
    }
    
    private Level level = Level.getLevel();
    
    /**
     * basic method of all actors | 
     * behaviour of all actors updated in basic Game class
     */
    public abstract void act();
    
    /**
     * finds out if object intersects with wall
     * @return false or true
     */
    public boolean intersectWithWall() {
        for(Walls w: level.getMap().getWalls()){
            if(w.intersects(this)){
                return true;
            }
            
        }
        return false;
    }
}
