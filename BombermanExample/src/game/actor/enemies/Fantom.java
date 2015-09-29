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
public class Fantom extends Enemies {

    private int generatedDirection;

    /**
     * sets animation, number of steps on start value
     *
     * @throws SlickException
     */
    public Fantom() throws SlickException {
        this.animation = new Animation(Anim.getAnimation("resources/actors/fantom", 11), 150);
        animation.setPingPong(true);
        steps = 0;
    }

    /**
     * provides default enemy behaviour and walking
     */
    @Override
    public void act() {
        super.act();
        walk();
        
    }

    /**
     * moves in specified direction
     */
    public void walk() {
        oldX = this.getX();
        oldY = this.getY();
        move();
        switch (generatedDirection) {
            case 0:
                direction = Direction.NORTH;
                this.y--;
                break;
            case 90:
                direction = Direction.EAST;
                this.x++;
                break;
            case 180:
                direction = Direction.SOUTH;
                this.y++;
                break;
            case 270:
                direction = Direction.WEST;
                this.x--;
                break;
        }
        steps--;
    }

    /**
     * randomly generates direction, checks if the next tile in the direction is
     * clear moves tile after tile - 32 steps in 1 direction
     */
    public void move() {
        if (steps == 0) {
            Random r = new Random();
            generatedDirection = r.nextInt(4) * 90;
            int fantomX = this.getX() / 32;
            int fantomY = this.getY() / 32;
            switch (generatedDirection) {
                case 0:
                    if (level.getMap().getWallMap()[fantomX][fantomY - 1] == 1) {
                        move();
                    }
                    break;
                case 90:
                    if (level.getMap().getWallMap()[fantomX + 1][fantomY] == 1) {
                        move();
                    }
                    break;
                case 180:
                    if (level.getMap().getWallMap()[fantomX][fantomY + 1] == 1) {
                        move();
                    }
                    break;
                case 270:
                    if (level.getMap().getWallMap()[fantomX - 1][fantomY] == 1) {
                        move();
                    }
                    break;
            }
            steps = 32;
        }
    }
}
