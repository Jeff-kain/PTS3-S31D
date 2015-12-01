/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import powerup.*;

/**
 *
 * @author jeffrey
 */
public class Player implements IGameObject {

    private Image sprite;
    private String name;
    private float blockSize;
    private float speed;
    private Boolean kick;
    private int bombCount;
    private SpriteSheet sprites;
    private Float x;
    private Float y;
    private Float bombRange;
    private Boolean visible;
    private Bomb_Up bomb_Up;
    private Game game = Game.getInstance();
    private TeamColor teamColor;
    private Direction kickDirection;

    private Playground playground = new Playground();
    private int respawn;

    public Float getBombRange() {
        return bombRange;
    }

    public void setBombRange(Float bombRange) {
        this.bombRange = bombRange;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public Player(SpriteSheet sprites, Float x, Float y, int bombCount, float speed, Boolean kick) throws SlickException {
        this.sprites = sprites;
        this.x = x;
        this.y = y;
        this.blockSize = 48f;
        this.speed = 200f;
        bomb_Up = new Bomb_Up(sprites, name, x, y, false);
        this.sprite = this.sprites.getSubImage(2, 16);
        this.name = name;
        this.bombRange = 0.5f;
        this.bombCount = bombCount;
        this.kick = kick;
        this.kickDirection = Direction.NONE;
        visible = true;
        respawn = 0;
    }

    @Override
    public void Update() {
        for (IGameObject o : game.playground().getMapobjects()) {
            if (o instanceof Explosion) {
                Explosion exp = (Explosion) o;

                if (exp.intersects(this)) {
                    if (this.teamColor == TeamColor.BLUE && visible == true) {
                        game.getTeam1().damage(50f);
                    }
                    if (this.teamColor == TeamColor.GREEN && visible == true) {
                        game.getTeam2().damage(50f);

                    }
                    this.visible = false;
                    respawn = 500;
                }
            }
        }
        if (respawn > 0) {
            respawn--;
        }
        if (respawn == 0 && visible == false) {
            if (teamColor == TeamColor.BLUE) {
                setPosition(96, 48);

            } else {
                setPosition(624, 576);

            }
            this.visible = true;
        }
        for (IGameObject o : game.playground().getPowerups()) {
            if (o instanceof Bomb_Up) {
                Bomb_Up bu = (Bomb_Up) o;

                if (bu.intersects(this)) {
                    if (bombCount < 4) {
                        setBombCount(getBombCount() + 1);
                    }
                    game.playground().removePowerup(bu);
                }
            }
            if (o instanceof Explosion_Up) {
                Explosion_Up eu = (Explosion_Up) o;

                if (eu.intersects(this)) {
                    if (getBombRange() < 2.5f) {
                        setBombRange(getBombRange() + 0.5f);
                    }
                    game.playground().removePowerup(eu);
                }
            }
            if (o instanceof Speed_Up) {
                Speed_Up su = (Speed_Up) o;

                if (su.intersects(this)) {
                    if (getSpeed() > 100f) {
                        setSpeed(getSpeed() - 50f);
                    }
                    game.playground().removePowerup(su);
                }
            }

            if(o instanceof Kick) {
                Kick k = (Kick)o;

                if(k.intersects(this)) {
                    this.setKick(true);
                    game.playground().removePowerup(k);
                }
            }
        }
    }

    public void reloadSprite(Direction direction) {
        int row = 0;
        kickDirection = direction;

        if (teamColor == teamColor.BLUE) {
            row = 16;
        } else {
            row = 17;
        }

        switch (direction.name()) {
            case "NORTH":
                this.sprite = this.sprites.getSubImage(8, row);
                break;
            case "EAST":
                this.sprite = this.sprites.getSubImage(6, row);
                break;
            case "SOUTH":
                this.sprite = this.sprites.getSubImage(2, row);
                break;
            case "WEST":
                this.sprite = this.sprites.getSubImage(6, row);
                this.sprite = this.sprite.getFlippedCopy(true, false);
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

    public Direction getKickDirection(){ return kickDirection; }

    public void setKickDirection(Direction direction) { kickDirection = direction; }

    public void setPosition(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public void setTeamColor(TeamColor teamColor) {
        this.teamColor = teamColor;
        if (teamColor == TeamColor.BLUE) {
            sprite = sprites.getSubImage(2, 16);
            System.out.println("Foobar");
        } else {
            sprite = sprites.getSubImage(2, 17);
        }
    }

    public boolean upBomb() {
        if (bombCount <= 5) {
            if (bomb_Up.isVisible()) {
                for (PowerUp b : playground.getPowerups()) {
                    if (b instanceof Bomb_Up) {
                        if (b.intersects(this)) {
                            ((Bomb_Up) b).setVisible(false);
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public void setBombCount(int bombCount) {
        this.bombCount++;
    }

    // Movements for player
    public void moveLeft() {

        this.x -= blockSize;

    }

    public void moveRight() {

        this.x += blockSize;

    }

    public void moveDown() {

        this.y += blockSize;
    }

    public void moveUp() {
        this.y -= blockSize;

    }

    public boolean intersectWithBox() {
        for (Box w : game.playground().getBoxes()) {
            if (w.intersects(this)) {
                System.out.println(this.getX());
                return true;
            }
        }
        return false;
    }

    public boolean intersectWithWall() {
        for (Wall w : game.playground().getWalls()) {
            if (w.intersects(this)) {
                return true;
            }
        }
        return false;
    }
    public boolean intersectWithBomb() {
        return false;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
