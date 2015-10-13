/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author jeffrey
 */
public class Player {
    private Image sprite;
    private String name;
    private float speed;
    private Boolean kick;
    private int Bomb_Count;
    private SpriteSheet sprites;
    private Float x;
    private Float y;
    
        public Player(SpriteSheet sprites, Float x, Float y) {
        this.sprites = sprites;
        this.x = x;
        this.y = y;
        this.speed = 48f;
        this.sprite = this.sprites.getSubImage(2, 16);
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
    

    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float Speed) {
        this.speed = speed;
    }

    public Boolean getKick() {
        return kick;
    }

    public void setKick(Boolean kick) {
        this.kick = kick;
    }

    public int getBomb_Count() {
        return Bomb_Count;
    }

    public void setBomb_Count(int Bomb_Count) {
        this.Bomb_Count = Bomb_Count;
    }
    
    // Movements for player
    public void moveLeft() {
        this.x -=speed;
    }
    
    public void moveRight()
    {
        this.x += speed;
    }
    
    public void moveDown() {
        this.y += speed;
    }
    
    public void moveUp()
    {
        this.y -=speed;
    }
    
    
    
    
}
