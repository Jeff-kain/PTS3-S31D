/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.enemies;

import game.Anim;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Blob extends Enemies {
    //   private int randomSteps;

    private int randomDirection;

    /**
     * sets animations, direction and number of steps on start value
     *
     * @throws SlickException
     */
    public Blob() throws SlickException {
        leftAnimation = new Animation(Anim.getAnimation("resources/actors/blob_w", 1), 250);
        rightAnimation = new Animation(Anim.getAnimation("resources/actors/blob_e", 1), 250);
        super.animation = this.leftAnimation;
        this.animation.start();
        direction = Direction.WEST;
        steps = 0;
    }

    /**
     * provides default enemy behaviour and walking
     */
    @Override
    public void act() {
        walk();
        super.act();
    }

    /**
     * moves in randomly generated direction for randomly generated number of
     * steps
     */
    public void walk() {
        oldX = this.getX();
        oldY = this.getY();
        if (steps == 0) {
            generateMovement();
        }
        switch (randomDirection) {
            case 0:
                direction = Direction.NORTH;
                this.y--;
                break;
            case 90:
                direction = Direction.EAST;
                this.animation = rightAnimation;
                this.x++;
                break;
            case 180:
                direction = Direction.SOUTH;
                this.y++;
                break;
            case 270:
                direction = Direction.WEST;
                this.animation = leftAnimation;
                this.x--;
                break;
        }
        steps--;

    }

    /**
     * generates direction and number of steps
     */
    public void generateMovement() {
        Random r = new Random();
        randomDirection = r.nextInt(4) * 90;
        steps = r.nextInt(50) + 3;
    }
}
