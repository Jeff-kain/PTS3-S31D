/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.AI;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

/**
 *
 * @author Michal
 */
public class Stalker {
    private PathFinder pathFinder;
    private Map map;
    private Path path;
    
    /**
     * creates new map for path-finding algorithm, instance of AStar path-finding object
     */
    public Stalker(){
        map = new Map();
        pathFinder = new AStarPathFinder(map,100,false);
        
    }
    
    /**
     * gets path from specified enemy(sx,sy) to player(tx,ty)
     * @param mover instance of chasing enemy - cron
     * @param sx X-coordination of start location
     * @param sy X-coordination of start location
     * @param tx X-coordination of target location
     * @param ty Y-coordination of target location
     * @return
     */
    public Path getPath(Mover mover, int sx, int sy, int tx, int ty){
        path = pathFinder.findPath(mover, sx, sy, tx, ty);
        return path;
    }
}
