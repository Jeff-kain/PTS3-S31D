/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.MapObjects;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Michal
 */
public class Walls extends MapObjects {

    /**
     * abstract class for wall type - block or wall
     */
    public Walls(){
}    
   
    /**
     * overrides intersect method, specified for this concrete class
     * @param actor
     * @return
     */
    @Override
    public boolean intersects(MapObjects actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX()+4, actor.getY()+4, actor.getAnimation().getWidth()-8, actor.getAnimation().getHeight()-8);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), 32, 32);
        return objekt.intersects(predmet);    
  }
  
}
