/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.newdawn.slick.Animation;

/**
 * 
 * @author Michal
 */
public interface Objects {
    
    /**
      * gets x-coordinate of current object
     * @return x-coordinate
     */
    public int getX();

    /**
      * gets y-coordinate of current object
     * @return y-coordinate
     */
    public int getY();

    /**
     * gets width of animation of current object
     * @return width of animation
     */
    public int getWidth();

    /**
     * gets height of animation of current object
     * @return height of animation
     */
    public int getHeight();

    /**
     *  gets primary animation
     * @return actual animation
     */
    public Animation getAnimation();

    /**
     * sets actual animation
     * @param anmtn amimation to be set
     */
    public void setAnimation(Animation anmtn);
    
    /**
     * sets coordinates of position
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setPosition(int x, int y);
    
}
