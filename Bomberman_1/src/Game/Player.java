/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import powerup.Bomb_Up;

/**
 *
 * @author jeffrey
 */
public class Player implements IGameObject {

    private Image sprite;
    private String name;
    private float speed;
    private Boolean kick;
    private int bombCount;
    private SpriteSheet sprites;
    private Float x;
    private Float y;
    private Float bombRange;
    private Boolean visible;
    private Bomb_Up bomb_Up;
    private Game game = new Game();
    private TeamColor teamColor;

    public Float getBombRange() {
        return bombRange;
    }

    public void setBombRange(Float bombRange) {
        this.bombRange = bombRange;
    }
    
    public Player(SpriteSheet sprites, Float x, Float y) {
        this.sprites = sprites;
        this.x = x;
        this.y = y;
        this.speed = 48f;
        bomb_Up = new Bomb_Up(name, x, y, visible);
        this.sprite = this.sprites.getSubImage(2, 16);
        this.name = name;
        this.bombRange = 2f;
    }

    @Override
    public void Update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void reloadSprite(Direction direction) {
        int row = 0;

        if(teamColor == teamColor.BLUE) {
            row = 16;
        } else {
            row = 14;
        }

        switch (direction.name()) {
            case "NORTH":
                this.sprite = this.sprites.getSubImage(8,row);
                break;
            case "EAST":
                this.sprite = this.sprites.getSubImage(6,row);
                break;
            case "SOUTH":
                this.sprite = this.sprites.getSubImage(2,row);
                break;
            case "WEST":
                this.sprite = this.sprites.getSubImage(6,row);
                this.sprite = this.sprite.getFlippedCopy(true,false);
                break;
        }
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
        if(teamColor == TeamColor.BLUE) {
            sprite = sprites.getSubImage(2, 16);
            System.out.println("Foobar");
        } else {
            sprite = sprites.getSubImage(2, 15);
        }
    }
    
    public void setBomb_Count(int Bomb_Count) {
        while (Bomb_Count <= 3) {
            if (bomb_Up.isVisible() == true) {

            }
        }

    }

    // Movements for player
    public void moveLeft() {

            this.x -= speed;
        
    }

    public void moveRight() {

            this.x += speed;
        
    }

    public void moveDown() {

            this.y += speed;
    }

    public void moveUp() {
            this.y -= speed;

    }
    public boolean intersectWithWall() {
        for (Box w : game.playground().getBoxes()) {
            if (w.intersects(this)) {
                System.out.println(this.getX());
                return true;
            }
        }
        return false;
    }
    

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
