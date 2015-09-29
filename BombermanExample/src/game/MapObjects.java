/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;

/**
 *
 * @author Michal
 */
public abstract class MapObjects implements Objects {
    /**
     * X-coordinate of object
     */
    protected int x;
    /**
     * Y-coordinate of object
     */
    protected int y;
    /**
     * animation of object
     */
    protected Animation animation;   
    
    /**
     * gets x-coordinate of current object
     * @return x-coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * gets y-coordinate of current object
     * @return y-coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     *  gets primary animation
     * @return actual animation
     */
    public Animation getAnimation() {
        return this.animation;
    }

    /**
     * sets actual animation
     * @param animation amimation to be set
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    /**
     * gets width of animation of current object
     * @return width of animation
     */
    public int getWidth() {
        return animation.getWidth();
    }

    /**
     * gets height of animation of current object
     * @return height of animation
     */
    public int getHeight() {
        return animation.getHeight();
    }
    
    /**
     * sets coordinates of position
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * finds out if two objects intersect, based on their animations
     * @param actor one of objects of possible intersection
     * @return
     */
    public boolean intersects(MapObjects actor) {      
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), actor.getAnimation().getWidth(), actor.getAnimation().getHeight());
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), this.animation.getWidth(), this.animation.getHeight());
        return objekt.intersects(predmet);
    }

}
