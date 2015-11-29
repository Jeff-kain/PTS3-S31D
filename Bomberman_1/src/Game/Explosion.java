/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author jeffrey
 */
public class Explosion implements IGameObject {

    private int explosionTimer;
    private float x;
    private float y;
    private Image sprite;

    public Explosion(SpriteSheet sprites, Direction direction) throws SlickException {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            this.sprite = sprites.getSubImage(14, 14);
        } else {
            this.sprite = sprites.getSubImage(1, 18);
        }
        explosionTimer = 60;
    }

    public Explosion(SpriteSheet sprites) throws SlickException {

        this.sprite = sprites.getSubImage(2, 18);
        explosionTimer = 60;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Image getSprite() {
        return sprite;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public void Update() {
        if (explosionTimer < 0) {
            game.playground().removeFromLevel(this);
        }
        explosionTimer--;
    }

    public boolean intersects(IGameObject actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX() , actor.getY() , 48, 48);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX() , this.getY(), 48f, 48f);
        return objekt.intersects(predmet);
    }

}
