/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.enemies;

import game.Anim;
import game.actor.AI.Stalker;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

/**
 *
 * @author Michal
 */
public class Cron extends Enemies implements Mover {

//    private int randomSteps;
    private int generatedDirection;
    private Stalker stalker;
    private Path path;
    private int stepSize;

    /**
     * sets animations, direction and number of steps on start value | gets
     * instance of path-finding class
     *
     * @throws SlickException
     */
    public Cron() throws SlickException {
        leftAnimation = new Animation(Anim.getAnimation("resources/actors/cron_w", 1), 250);
        rightAnimation = new Animation(Anim.getAnimation("resources/actors/cron_e", 1), 250);
        super.animation = this.leftAnimation;
        direction = Direction.WEST;
        steps = 0;
        stalker = new Stalker();
        stepSize = 32;
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
     * checks if exists path to player | moves in the specified direction
     *
     */
    public void walk() {
        oldX = this.getX();
        oldY = this.getY();
        if (path != null) {
            stepSize--;
        } else {
            stepSize = 32;
        }
        if (stepSize == 0) {
            stepSize = 32;
        }
        stalk();
        switch (generatedDirection) {
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
     * randomly generates direction and checks if the tile in the direction is
     * clear(not wall) sets steps on 32 - 32 pixels of 1 tile, 1 step = 1 pixel
     */
    public void move() {
        if (steps == 0) {
            Random r = new Random();
            generatedDirection = r.nextInt(4) * 90;
            int cronX = this.getX() / 32;
            int cronY = this.getY() / 32;
            switch (generatedDirection) {
                case 0:
                    if (level.getMap().getWallMap()[cronX][cronY - 1] == 1) {
                        move();
                    }
                    break;
                case 90:
                    if (level.getMap().getWallMap()[cronX + 1][cronY] == 1) {
                        move();
                    }
                    break;
                case 180:
                    if (level.getMap().getWallMap()[cronX][cronY + 1] == 1) {
                        move();
                    }
                    break;
                case 270:
                    if (level.getMap().getWallMap()[cronX - 1][cronY] == 1) {
                        move();
                    }
                    break;
            }
            steps = 32;
        }
    }

    /**
     * checks if exists clear path to player | if exists, sets direction to the
     * first tile of the acquired path | else moves randomly | moves tile after
     * tile - 32 steps in 1 direction
     */
    public void stalk() {
        if (steps == 0) {
            path = stalker.getPath(this, this.getX() / 32, this.getY() / 32, (level.getPlayer().getX() + 10) / 32, (level.getPlayer().getY() + 10) / 32);
        }
        if (path == null) {
            move();
        } else {
            steps = stepSize;
            if (path.getStep(1).getX() > this.getX() / 32) {
                generatedDirection = 90;
            }
            if (path.getStep(1).getX() < this.getX() / 32) {
                generatedDirection = 270;
            }
            if (path.getStep(1).getY() > this.getY() / 32) {
                generatedDirection = 180;
            }
            if (path.getStep(1).getY() < this.getY() / 32) {
                generatedDirection = 0;
            }
        }
    }
}
