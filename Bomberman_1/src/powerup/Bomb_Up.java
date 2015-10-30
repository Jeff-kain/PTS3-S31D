/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerup;

import Game.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.awt.geom.Rectangle2D;
import java.io.File;

import static java.io.File.separator;

/**
 *
 * @author jeffrey
 */
public class Bomb_Up extends PowerUp{

    public Float x;
    public Float y;
    public Boolean visible = false;
    public String name = "Bomb_Up";
    private Image sprite;
    private SpriteSheet sprites;

    public Bomb_Up(SpriteSheet sprites, String name, Float x, Float y, Boolean visible) throws SlickException {
        super(name, x, y);
        this.sprites = new SpriteSheet("res" + File.separator + "Powerups.png", 48, 48, Color.decode("#FF00FF"));
        this.sprite = this.sprites.getSubImage(2, 0);
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setPosition(Float x, Float y)
    {
        this.x = x;
        this.y = y;
    }

    public Image getSprite() {
        return sprite;
    }

    public Boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }


}
