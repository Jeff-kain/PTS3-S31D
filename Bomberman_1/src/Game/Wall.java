/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author jeffrey
 */
public class Wall implements IGameObject {

    private Image sprite;
    private SpriteSheet sprites;
    private Float x;
    private Float y;

    public Wall(SpriteSheet sprites, Float x, Float y) {
        this.sprites = sprites;
        this.x = x;
        this.y = y;
        this.sprite = this.sprites.getSubImage(9, 12);
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
    }

    public boolean intersects(IGameObject actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), 48f, 48f);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), 48f, 48f);
        return objekt.intersects(predmet);
    }

}
