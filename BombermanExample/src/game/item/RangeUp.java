/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.item;

import game.Anim;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class RangeUp extends Items{
    
    /**
     * sets animation
     * @throws SlickException
     */
    public RangeUp() throws SlickException{
        animation = new Animation(Anim.getAnimation("resources/items/range", 1),20);
    }
    
}
