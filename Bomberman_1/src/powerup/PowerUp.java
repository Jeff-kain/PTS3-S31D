/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerup;

import Game.IGameObject;
import Game.Player;
import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Image;

/**
 *
 * @author jeffrey
 */
public class PowerUp implements IGameObject {

    private final String powerUp;
    private Float x;
    private Float y;

    public PowerUp(String powerup, Float x, Float y) {
        this.powerUp = powerup;
        this.x = x;
        this.y = y;
    }

    public boolean intersects(Player actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), 48f, 48f);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), 48f, 48f);
        return objekt.intersects(predmet);
    }

    @Override
    public Image getSprite() {
        return null;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public void Update() {

    }

    @Override
    public boolean intersects(IGameObject actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), 48f, 48f);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), 48f, 48f);
        return objekt.intersects(predmet);
    }

}
