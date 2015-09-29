/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.Anim;
import game.Level;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Wall extends Walls {
    Level level = Level.getLevel();
    /**
     * destroyable wall | sets animations 
     * @throws SlickException
     */
    public Wall() throws SlickException{
        switch(level.getLevelNumber()){
            case 1: animation = new Animation(Anim.getAnimation("resources/map/wall", 1),200);
                break;
            case 2: animation = new Animation(Anim.getAnimation("resources/map/wallb", 1),200);
                break;
            case 3: animation = new Animation(Anim.getAnimation("resources/map/wallc", 1),200);
                break;
            default: animation = new Animation(Anim.getAnimation("resources/map/wall", 1),200);
        }
    }
    
}
