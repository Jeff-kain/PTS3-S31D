/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.Anim;
import game.MapObjects;
import game.item.Items;
import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Portal extends Items {
    
    /**
     * sets animation
     * @throws SlickException
     */
    public Portal() throws SlickException{
        animation = new Animation(Anim.getAnimation("resources/map/portal", 1),180);
    }
    
    /**
     * overrides intersect method, specified for this concrete class
     * @param actor
     * @return
     */
    @Override
    public boolean intersects(MapObjects actor) {    
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX()+6, actor.getY()+6, 20, 20);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX()+15, this.getY()+15, this.animation.getWidth()-30, this.animation.getHeight()-30);
        return objekt.intersects(predmet);                    
    }
    
}
