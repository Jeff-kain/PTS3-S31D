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
public class DHead extends Dragon {

    private int generatedDirection = 0;
    private int oldDirection = 0;

    /**
     * sets animations, steps on default value - 32 (1 tile)
     * @throws SlickException
     */
    public DHead() throws SlickException {
        super();
        leftAnimation = new Animation(Anim.getAnimation("resources/actors/dhead_w", 1), 100);
        rightAnimation = new Animation(Anim.getAnimation("resources/actors/dhead_e", 1), 100);
        downAnimation = new Animation(Anim.getAnimation("resources/actors/dhead_s", 1), 100);
        upAnimation = new Animation(Anim.getAnimation("resources/actors/dhead_n", 1), 100);
        this.animation = upAnimation;
        steps = 32;
    }

    /**
     * moves in specified direction
     */
    public void act() {
        oldX = this.getX();
        oldY = this.getY();
        move();
        switch (generatedDirection) {
            case 0:
                this.animation = upAnimation;
                direction = Direction.NORTH;
                this.y--;
                break;
            case 90:
                this.animation = rightAnimation;
                direction = Direction.EAST;
                this.x++;
                break;
            case 180:
                this.animation = downAnimation;
                direction = Direction.SOUTH;
                this.y++;
                break;
            case 270:
                this.animation = leftAnimation;
                direction = Direction.WEST;
                this.x--;
                break;
        }
        steps--;
    }

    /**
     * generates direction, checks if the next tile in the direction is clear |
     * sets number of steps on 32
     */
    public void move() {
        if (steps == 2) {
            oldDirection = generatedDirection;
        }
        if (steps == 0) {
            generatedDirection = oldDirection;
            int number = generate();
            if (checkPath() < 3) {
                while (number == 180) {
                    number = generate();
                }
            }

            generatedDirection += number;
            if (generatedDirection > 270) {
                generatedDirection -= 360;
            }
            int dragonX = this.getX() / 32;
            int dragonY = this.getY() / 32;
            switch (generatedDirection) {
                case 0:
                    if (level.getMap().getWallMap()[dragonX][dragonY - 1] == 1) {
                        move();
                    }
                    break;
                case 90:
                    if (level.getMap().getWallMap()[dragonX + 1][dragonY] == 1) {
                        move();
                    }
                    break;
                case 180:
                    if (level.getMap().getWallMap()[dragonX][dragonY + 1] == 1) {
                        move();
                    }
                    break;
                case 270:
                    if (level.getMap().getWallMap()[dragonX - 1][dragonY] == 1) {
                        move();
                    }
                    break;
            }
            steps = 32;
        }
    }

    private int checkPath() {
        int number = 0;
        int dragonX = this.getX() / 32;
        int dragonY = this.getY() / 32;
        if (level.getMap().getWallMap()[dragonX][dragonY - 1] == 1) {
            number++;
        }
        if (level.getMap().getWallMap()[dragonX + 1][dragonY] == 1) {
            number++;
        }
        if (level.getMap().getWallMap()[dragonX - 1][dragonY] == 1) {
            number++;
        }
        if (level.getMap().getWallMap()[dragonX][dragonY + 1] == 1) {
            number++;
        }
        return number;
    }

    /**
     * generates number/direction 
     * @return generated number
     */
    public int generate() {
        Random r = new Random();
        return r.nextInt(4) * 90;
    }

    /**
     * indicates that body part after head can move immediately (after 32 steps)
     * @return
     */
    public boolean canMove() {
        return true;
    }

    /**
     * gets previous direction
     * @return previous direction
     */
    public Direction getDirection() {
        switch (oldDirection) {
            case 0:
                return Direction.NORTH;
            case 90:
                return Direction.EAST;
            case 180:
                return Direction.SOUTH;
            case 270:
                return Direction.WEST;
        }
        return direction;
    }
}
