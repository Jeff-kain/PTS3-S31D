/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.File;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author jeffrey
 */
public class Player implements IGameObject{

    private String name;
    private TeamColor teamColor;
    private int speed;
    private Boolean kick;
    private int bombCount;
    private SpriteSheet sprites;
    private Image sprite;
    private Float x;
    private Float y;

    public Player(SpriteSheet sprites, String name) {
        this.sprites = sprites;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int Speed) {
        this.speed = Speed;
    }

    public Boolean getKick() {
        return kick;
    }

    public void setKick(Boolean kick) {
        this.kick = kick;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }
    
    public void setPosition(Float x, Float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setTeamColor(TeamColor teamColor) {
        this.teamColor = teamColor;
        if(teamColor == TeamColor.Blue) {
            sprite = sprites.getSubImage(2, 16);
            System.out.println("Foobar");
        } else {
            sprite = sprites.getSubImage(2, 15);
        }
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public Float getX() {
        return x;
    }

    @Override
    public Float getY() {
        return y;
    }
    

    
    
    
}
