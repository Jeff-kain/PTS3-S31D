/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Multiplayer.IRemoteClient;
import Multiplayer.Manager;
import bomberman.Game_StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import powerup.Bomb_Up;
import powerup.Explosion_Up;
import powerup.Kick;
import powerup.Speed_Up;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.net.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * @author jeffrey
 */
public class Player implements IGameObject, Serializable {

    boolean pleft, pright, pup, pdown, plant;
    private Image sprite;
    private String name;
    private float blockSize;
    private float speed;
    private Boolean kick;
    private int bombCount;
    private int placedBombs;
    private SpriteSheet sprites;
    private Float x;
    private Float y;
    private Float bombRange;
    private Boolean visible;
    private Bomb_Up bomb_Up;
    private Game game = Game.getInstance();
    private TeamColor teamColor;
    private int kickDirection;
    private Playground playground = new Playground();
    private int respawn;
    private Manager manager = Manager.getManager();
    private Keyset Keys;
    Timer timer = new Timer();

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
        this.kickDirection = 0;
        visible = true;
        respawn = 0;

    }

    public Float getBombRange() {
        return bombRange;
    }

    public void setBombRange(Float bombRange) {
        this.bombRange = bombRange;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(TeamColor teamColor) {
        this.teamColor = teamColor;
        if (teamColor == TeamColor.BLUE) {
            sprite = sprites.getSubImage(2, 16);
        } else {
            sprite = sprites.getSubImage(2, 17);
        }
    }

    public int getPlacedBombs() {
        return placedBombs;
    }

    public void addPlacedBombs() {
        placedBombs += 1;
    }

    public void deletePlacedBombs() {
        placedBombs -= 1;
    }

    @Override
    public void Update() {
        if (respawn <= 0) {
//            if (keycode == Keys.KeyUp) {
//                pup = pressed;
//            }
//            if (keycode == Keys.KeyLeft) {
//                pleft = pressed;
//            }
//            if (keycode == Keys.KeyDown) {
//                pdown = pressed;
//            }
//            if (keycode == Keys.KeyRight) {
//                pright = pressed;
//            }
//            if (keycode == Keys.KeyBomb) {
//                plant = pressed;
//            }
        }
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
                    respawn = 400;
                }
            }
        }
        if (respawn > 0) {
            respawn--;
        }
        if (respawn == 0 && visible == false) {
            if (teamColor == TeamColor.BLUE) {
                setPosition(48, 575);

            } else {
                setPosition(624, 96);

            }
            this.visible = true;
        }
        for (IGameObject o : game.playground().getMapobjects()) {
            if (o instanceof Bomb_Up) {
                Bomb_Up bu = (Bomb_Up) o;

                if (bu.intersects(this)) {
                    if (bombCount < 4) {
                        setBombCount(getBombCount() + 1);
                    }
                    game.playground().removeFromLevel(bu);
                }
            }
            if (o instanceof Explosion_Up) {
                Explosion_Up eu = (Explosion_Up) o;

                if (eu.intersects(this)) {
                    if (getBombRange() < 3.5f) {
                        setBombRange(getBombRange() + 1f);
                    }
                    game.playground().removeFromLevel(eu);
                }
            }
            if (o instanceof Speed_Up) {
                Speed_Up su = (Speed_Up) o;

                if (su.intersects(this)) {
                    if (getSpeed() > 100f) {
                        setSpeed(getSpeed() - 50f);
                    }
                    game.playground().removeFromLevel(su);
                }
            }

            if (o instanceof Kick) {
                Kick k = (Kick) o;

                if (k.intersects(this)) {
                    this.setKick(true);
                    game.playground().removeFromLevel(k);
                }
            }
        }
    }

    public void reloadSprite(int direction) {
        int row = 0;
        kickDirection = direction;

        if (teamColor == teamColor.BLUE) {
            row = 16;
        } else {
            row = 17;
        }

        switch (direction) {
            case 1:
                this.sprite = this.sprites.getSubImage(8, row);
                break;
            case 4:
                this.sprite = this.sprites.getSubImage(6, row);
                break;
            case 3:
                this.sprite = this.sprites.getSubImage(2, row);
                break;
            case 2:
                this.sprite = this.sprites.getSubImage(6, row);
                this.sprite = this.sprite.getFlippedCopy(true, false);
                break;
        }
    }

    public int getRespawn() {
        return respawn;
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
        this.bombCount++;
    }

    public int getKickDirection() {
        return kickDirection;
    }

    public void setKickDirection(int direction) {
        kickDirection = direction;
    }

    public void setPosition(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public boolean upBomb() {
        if (bombCount <= 5) {
            if (bomb_Up.isVisible()) {
                for (IGameObject b : playground.getMapobjects()) {
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

    public void moveifremote(int direction) {

//        System.out.println("moveifremote: " + direction);
//        System.out.println("manager: " + manager.isBoolClient());
//        System.out.println("manager boolLAN: " + manager.isBoolLAN());
        try {
            if (manager.isBoolLAN()) {

                if (manager.isBoolClient()) {

                    manager.getRemotehost().movep2c(direction, this.x, this.y);
                    if (manager.getRemoteSpectate() != null) {
                        manager.getRemoteSpectate().movep2c(direction, this.x, this.y);
                    }
                } else {

                    manager.getRemoteclient().movep2h(direction, this.x, this.y);
                    if (manager.getRemoteSpectate() != null) {
                        manager.getRemoteSpectate().movep2h(direction, this.x, this.y);

                    }
                }
            }

        } catch (RemoteException e) {
            try {
                if (manager.isBoolClient()) {
                    UnicastRemoteObject.unexportObject(manager.getRemotehost(), true);
                } else {
                    UnicastRemoteObject.unexportObject(manager.getRemoteclient(), true);
                }
            } catch (NoSuchObjectException ex) {
                System.out.println("Connection lost");

                Game_StateBasedGame.game1.enterState(4);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Your database code here
                    }
                }, 5000);
            }
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getClass());
        }
    }

    public void moveremote(int direction, float x, float y) {
        System.out.println("moveremoteclient: " + direction);
        int row = 0;
        if (teamColor == teamColor.BLUE) {
            row = 16;
        } else {
            row = 17;
        }
        float oldx = this.getX();
        float oldy = this.getY();
        kickDirection = direction * -1;
        setPosition(x, y);
        if (direction == Keyset.REMUP) {
            this.sprite = this.sprites.getSubImage(8, row);
            //this.y -= blockSize;

        } else if (direction == Keyset.REMLEFT) {
            this.sprite = this.sprites.getSubImage(6, row);
            this.sprite = this.sprite.getFlippedCopy(true, false);
            //this.x -= blockSize;

        } else if (direction == Keyset.REMDOWN) {
            this.sprite = this.sprites.getSubImage(2, row);

            //this.y += blockSize;
        } else if (direction == Keyset.REMRIGHT) {
            this.sprite = this.sprites.getSubImage(6, row);

            //  this.x += blockSize;
        } else if (direction == Keyset.REMBOMB) {
            Bomb b = new Bomb(sprites, x, y, this.getBombRange());

            //this.setKickDirection(0);
            game.playground().addToLevel(b);
            System.out.println("bombermanClient added to level");

        }
        if (this.intersectWithBox() || this.intersectWithWall() || this.intersectWithPlayer() || this.intersectWithCastle()) {
            setPosition(oldx, oldy);
        }
    }

    /**
     * @param keycode
     * @param pressed
     */
    public void update(int keycode, boolean pressed) {
        // System.out.println("lanplayerupdate");
        if (respawn <= 0) {
            if (keycode == Keys.KeyUp) {
                pup = pressed;
            }
            if (keycode == Keys.KeyLeft) {
                pleft = pressed;
            }
            if (keycode == Keys.KeyDown) {
                pdown = pressed;
            }
            if (keycode == Keys.KeyRight) {
                pright = pressed;
            }
            if (keycode == Keys.KeyBomb) {
                plant = pressed;
            }
        }
    }

    public void move() {
        if (pup) {
            move(IRemoteClient.UP);
        } else if (pleft) {
            move(IRemoteClient.LEFT);
        } else if (pdown) {
            move(IRemoteClient.DOWN);
        } else if (pright) {
            move(IRemoteClient.RIGHT);
        } else if (plant) {
            move(IRemoteClient.BOMB);
        }
    }

    public void move(int direction) {
        int row = 0;
        kickDirection = direction;
        if (teamColor == teamColor.BLUE) {
            row = 16;
        } else {
            row = 17;
        }

        switch (direction) {
            case 1:
                this.sprite = this.sprites.getSubImage(8, row);
                this.y -= blockSize;
                moveifremote(direction);
                break;
            case 4:
                this.sprite = this.sprites.getSubImage(6, row);
                this.x += blockSize;
                moveifremote(direction);

                break;
            case 3:
                this.sprite = this.sprites.getSubImage(2, row);
                this.y += blockSize;
                moveifremote(direction);

                break;
            case 2:
                this.x -= blockSize;
                this.sprite = this.sprites.getSubImage(6, row);
                this.sprite = this.sprite.getFlippedCopy(true, false);
                moveifremote(direction);

                break;
            case 5:
                // TODO Bomb stuff
                moveifremote(direction);
                System.out.println("Bomb added???");

                break;

        }
    }

    public boolean intersectWithBox() {
        for (IGameObject w : game.playground().getMapobjects()) {
            if (w instanceof Box) {
                if (w.intersects(this)) {
                    return true;
                }
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

    public boolean intersectWithCastle() {
        if (game.getTeam1().intersects(this)) {
            return true;
        }
        if (game.getTeam2().intersects(this)) {
            return true;
        }
        return false;
    }

    public boolean intersectWithPlayer() {
        for (Player p : Game.getInstance().getAllPlayers()) {
            if (this != p) {
                if (this.intersects(p)) {
                    System.out.println("Player collision detected");
                    return true;
                }
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

    @Override
    public boolean intersects(IGameObject actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), 48f, 48f);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), 48f, 48f);
        return objekt.intersects(predmet);
    }
}
