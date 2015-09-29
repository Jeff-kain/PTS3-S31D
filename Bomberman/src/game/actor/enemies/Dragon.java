/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.enemies;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Dragon extends Enemies {

    private static List<Dragon> dragonbody = new ArrayList<Dragon>();
    /**
     *
     */
    protected boolean move;

    /**
     *
     */
    public Dragon() {
    }

    /**
     * creates all parts of dragon, add them into List
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @throws SlickException
     */
    public Dragon(int x, int y) throws SlickException {
        DHead head = new DHead();
        dragonbody.add(head);
        head.setPosition(x, y);

        DBody body1 = new DBody(head);
        dragonbody.add(body1);
        body1.setPosition(x, y);

        DBody body2 = new DBody(body1);
        dragonbody.add(body2);
        body2.setPosition(x, y);

//        DBody body3 = new DBody(body2);
//        dragonbody.add(body3);
//        body3.setPosition(x, y);

//        DBody body4 = new DBody(body3);
//        dragonbody.add(body4);
//        body4.setPosition(x, y);
        //      level.addToLevel(body4);
        //      level.addToLevel(body3);
        //      level.addToLevel(body2);
        //      level.addToLevel(body1);
        //      level.addToLevel(head);
    }

    /**
     * provides updating act methods of all dragon's body parts
     */
    @Override
    public void act() {
        for (Dragon dragon : dragonbody) {
            dragon.act();
        }
    }

    /**
     * gets List of all dragon's body parts
     *
     * @return List of dragon's parts
     */
    public static List<Dragon> getBody() {
        return dragonbody;
    }

    /**
     * indicates if concrete dragon's part can move (waits for going out from
     * portal)
     *
     * @return
     */
    public boolean canMove() {
        return move;
    }

    /**
     * gets number of steps before generating new direction
     *
     * @return number of steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * gets current direction
     *
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }
}
