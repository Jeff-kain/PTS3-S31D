/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.enemies;

import game.Anim;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class DBody extends Dragon {

    private int time;
    private Dragon head;
    private Direction oldDirection;

    /**
     * sets animations, waiting time on default value
     * @param head
     * @throws SlickException
     */
    public DBody(Dragon head) throws SlickException {
        super();
        leftAnimation = new Animation(Anim.getAnimation("resources/actors/dbody_w", 1), 100);
        rightAnimation = new Animation(Anim.getAnimation("resources/actors/dbody_e", 1), 100);
        downAnimation = new Animation(Anim.getAnimation("resources/actors/dbody_s", 1), 100);
        upAnimation = new Animation(Anim.getAnimation("resources/actors/dbody_n", 1), 100);
        this.animation = upAnimation;
        this.head = head;
        move = false;
        time = 32;

    }

    /**
     * after waiting time is done, moves in previous direction of dragon body part in front of this one
     */
    @Override
    public void act() {
        if (canMove()) {
            if (head.getSteps() == 0) {
                oldDirection = direction;
                direction = head.getDirection();
            }
            switch (direction) {
                case NORTH:
                    this.animation = upAnimation;
                    this.y--;
                    break;
                case EAST:
                    this.animation = rightAnimation;
                    this.x++;
                    break;
                case SOUTH:
                    this.animation = downAnimation;
                    this.y++;
                    break;
                case WEST:
                    this.animation = leftAnimation;
                    this.x--;
                    break;
            }
        }
        if (head.canMove() && time > 0) {
            time--;
        }
        if (time == 0) {
            move = true;
            direction=head.getDirection();
            time = -1;
        }


    }

    /**
     * indicates if this dragon body part can move - after waiting time is done
     * @return
     */
    public boolean canMove() {
        return move;
    }

    /**
     * gets previous direction
     * @return previous direction
     */
    @Override
    public Direction getDirection() {
        return oldDirection;
    }
}
