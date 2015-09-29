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
public class KickUp extends Items {
    
    /**
     * sets animation
     * @throws SlickException
     */
    public KickUp() throws SlickException{
        animation = new Animation(Anim.getAnimation("resources/items/kick", 1),20);
    }
}
