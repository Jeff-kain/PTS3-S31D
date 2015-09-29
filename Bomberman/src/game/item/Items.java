/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.item;

import game.MapObjects;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Michal
 */
public abstract class Items extends MapObjects {
    
    private boolean visible = false;
    
    /**
     * indicates if item is hidden behind wall
     * @return false - if hidden, true -  if not hidden
     */
    public boolean isVisible(){
        return visible;
    }
    
    /**
     * sets visibility
     * @param visible
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }
    
    /**
     * overrides intersect method, specified for this concrete class
     * @param actor
     * @return
     */
    @Override
    public boolean intersects(MapObjects actor) {    
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX()+6, actor.getY()+6, 18, 18);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX()+2, this.getY()+2, this.animation.getWidth()-4, this.animation.getHeight()-4);
        return objekt.intersects(predmet);                    
    }
}
