/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Anim;
import game.Level;
import game.MapObjects;
import game.actor.enemies.Broom;
import game.actor.enemies.Dragon;
import game.actor.enemies.Enemies;
import game.item.Items;
import game.map.Portal;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Flame extends Actors {

    private int time;
    private Level level = Level.getLevel();
    private boolean broomsMode = false;
    private int portalX;
    private int portalY;
    private int dragonTimer;

    /**
     * sets animation,basic time for animation
     * @param direction
     * @throws SlickException
     */
    public Flame(Direction direction) throws SlickException {
//        switch (direction){
//            case NORTH: 
//            animation = new Animation(Anim.getAnimation("resources/actors/flame_n", 4),250);
//                break;
//            case SOUTH: 
//                animation = new Animation(Anim.getAnimation("resources/actors/flame_s", 4),250);
//                break;
//            case EAST:
//                animation = new Animation(Anim.getAnimation("resources/actors/flame_e", 4),250);
//                break;
//            case WEST:
//                animation = new Animation(Anim.getAnimation("resources/actors/flame_w", 4),250);
//                break;
//        }
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            animation = new Animation(Anim.getAnimation("resources/actors/flame_ns", 1), 20);
        } else {
            animation = new Animation(Anim.getAnimation("resources/actors/flame_we", 1), 20);
        }
        time = 50;
        dragonTimer = 0;
    }

    /**
     * makes bombs explode when intersect with them | 
     * if intersect with dragon, removes the last part of his body |
     * if intersects with an item, burns it |
     * if intersects with portal, creates brooms - type of enemy 
     */
    @Override
    public void act() {
        if (dragonTimer>0){
            dragonTimer--;
        }
        for (int j = 0; j < level.getListOfObjects().toArray().length; j++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[j];
            if (o.intersects(this) && o instanceof Bombs) {
                ((Bombs) o).setExplodeTime(0);
            }
            if (o instanceof Dragon && dragonTimer==0) {
                for (int i = 0; i< Dragon.getBody().toArray().length; i++) {
                    Dragon d = (Dragon) Dragon.getBody().toArray()[i];
                    if (d.intersects(this)) {
                        List<Dragon> dragon = Dragon.getBody();
                        dragon.remove(dragon.size()-1);
                        dragonTimer=50;
                    }
                } 
            }

            if (o.intersects(this) && o instanceof Items && ((Items) o).isVisible() && !(o instanceof Portal)) {
                level.removeFromLevel(o);
            }
            if (o instanceof Portal && o.intersects(this) && ((Portal) o).isVisible()) {
                broomsMode = true;
                portalX = o.getX();
                portalY = o.getY();
            }
        }
        if (time == 0) {
            level.getListOfObjects().remove(this);
            if (broomsMode) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Broom broom = new Broom();
                        broom.setPosition(portalX, portalY);
                        level.addToLevel(broom);
                    } catch (SlickException ex) {
                        ex.printStackTrace();
                    }
                }
                broomsMode = false;
            }
        } else {
            time--;
        }
    }

    /**
     * overrides intersect method, specified for this concrete class
     * @param actor
     * @return
     */
    @Override
    public boolean intersects(MapObjects actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX() + 6, actor.getY() + 6, 18, 18);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX() + 2, this.getY() + 2, this.animation.getWidth() - 4, this.animation.getHeight() - 4);
        return objekt.intersects(predmet);
    }
}
