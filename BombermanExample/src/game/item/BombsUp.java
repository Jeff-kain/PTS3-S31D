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
public class BombsUp extends Items{
    
    /**
     * sets animation
     * @throws SlickException
     */
    public BombsUp() throws SlickException{
        animation = new Animation(Anim.getAnimation("resources/items/bomb", 1), 20);
    }
    
}
