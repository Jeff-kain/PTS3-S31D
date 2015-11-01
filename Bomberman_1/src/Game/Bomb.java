/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author tverv
 */
public class Bomb implements IGameObject {

    private Image sprite;
    private SpriteSheet sprites;
    private Float x;
    private Float y;
    private int explodeTime;
    private Animation explodingBomb;
    private boolean exploded;
    private Float range;
    private final int duration = 550;

    public int getExplodeTime() {
        return explodeTime;
    }

    public boolean isExploded() {
        return exploded;
    }
    private boolean intersectWithPlayer;
    private final static int STEP = 2;
    private int steps;

    public Bomb(SpriteSheet sprites, Float x, Float y, float range) {
        this.sprites = sprites;
        this.x = x;
        this.y = y;
        this.explodeTime = 200;
        this.sprite = this.sprites.getSubImage(11, 15);
        this.exploded = false;
        this.range = range;
    }

    public Animation getAnimation() {


        int[] frames = {4, 18, 5, 18, 6, 18, 7, 18, 8, 18, 9, 18};
        int[] durations = {duration, duration, duration, duration, duration, duration};
        Animation animation = new Animation(sprites, frames, durations);

        return animation;
    }

    public Image getSprite() {
        return sprite;
    }

    public Float getX() {
        return x;
    }

    public Float getY() { return y; }


    @Override
    public void Update() {
        if (explodeTime < 0) {
            exploded = true;
            try {
                createFlames();
            } catch (SlickException ex) {
                ex.printStackTrace();
            }
        }
        explodeTime--;
    }

    public void createFlames() throws SlickException {
        lookAround(Direction.EAST, range);
        lookAround(Direction.WEST, range);
        lookAround(Direction.NORTH, range);
        lookAround(Direction.SOUTH, range);
    }

    public void lookAround(Direction direction, Float range) throws SlickException {
        Float oldX = this.getX();
        Float oldY = this.getY();
        switch (direction) {
            case EAST:
                for (int r = 0; r < range; r++) {
                    this.x += (48 + 48 * r);
                    Explosion flameMid = new Explosion(sprites);
                    flameMid.setPosition(oldX, oldY);
                    game.playground().addToLevel(flameMid);
                    if (!this.intersectWithWall()) {
                        Explosion flame = new Explosion(sprites, Direction.EAST);
                        flame.setPosition(oldX + 48 + 48 * r, oldY);
                        game.playground().addToLevel(flame);
                        checkForBase(this.getX(), this.getY());
                    } else {
                        checkForBox(this.getX(), this.getY(), Direction.EAST);
                        this.x -= (48 + 48 * r);
                        break;
                    }
                    this.x -= (48 + 48 * r);
                }
                break;
            case WEST:
                for (int r = 0; r < range; r++) {
                    this.x -= (48 + 48 * r);
                    if (!this.intersectWithWall()) {
                        Explosion flame = new Explosion(sprites, Direction.WEST);
                        flame.setPosition(oldX - 48 - 48 * r, oldY);
                        game.playground().addToLevel(flame);
                        checkForBase(this.getX(), this.getY());

                    } else {
                        checkForBox(this.getX(), this.getY(), Direction.WEST);
                        this.x += (48 + 48 * r);
                        break;
                    }
                    this.x += (48 + 48 * r);
                }
                break;
            case SOUTH:
                for (int r = 0; r < range; r++) {
                    this.y += (48 + 48 * r);
                    if (!this.intersectWithWall()) {
                        Explosion flame = new Explosion(sprites, Direction.SOUTH);
                        flame.setPosition(oldX, oldY + 48 + 48 * r);
                        game.playground().addToLevel(flame);
                        checkForBase(this.getX(), this.getY());

                    } else {
                        checkForBox(this.getX(), this.getY(), Direction.SOUTH);
                        this.y -= (48 + 48 * r);
                        break;
                    }
                    this.y -= (48 + 48 * r);
                }
                break;
            case NORTH:
                for (int r = 0; r < range; r++) {
                    this.y -= (48 + 48 * r);
                    if (!this.intersectWithWall()) {
                        Explosion flame = new Explosion(sprites, Direction.NORTH);
                        flame.setPosition(oldX, oldY - 48 - 48 * r);
                        game.playground().addToLevel(flame);
                        checkForBase(this.getX(), this.getY());

                    } else {
                        checkForBox(this.getX(), this.getY(), Direction.NORTH);
                        this.y += (48 + 48 * r);
                        break;
                    }
                    this.y += (48 + 48 * r);
                }
                break;
        }

    }

    public void checkForBox(float x, float y, Direction d) {
        for (Box o : game.playground().getBoxes()) {
            if (o.getX() == x && o.getY() == y) {
                Explosion flame = null;
                try {
                    flame = new Explosion(sprites, d);
                } catch (SlickException ex) {
                    Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
                }
                flame.setPosition(o.getX(), o.getY());
                game.playground().addToLevel(flame);
                game.playground().RemoveBox(o);
            }
        }
//        for (Player p : game.getTeam1().getPlayers()) {
//            if (p.getX() == x && p.getY() == y) {
//                Explosion flame = null;
//                try {
//                    flame = new Explosion(sprites, d);
//                } catch (SlickException ex) {
//                    Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                flame.setPosition(p.getX(), p.getY());
//                game.playground().addToLevel(flame);
//            }
//        }
//        for (Player p : game.getTeam2().getPlayers()) {
//            if (p.getX() == x && p.getY() == y) {
//                Explosion flame = null;
//                try {
//                    flame = new Explosion(sprites, d);
//                } catch (SlickException ex) {
//                    Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                flame.setPosition(p.getX(), p.getY());
//                game.playground().addToLevel(flame);
//            }
//        }
    }

    public void checkForBase(float x, float y) {
        if (game.getTeam1().getX() == x && game.getTeam1().getY() == y) {
            game.getTeam1().damage();
        }
        if (game.getTeam2().getX() == x && game.getTeam2().getY() == y) {
            game.getTeam2().damage();
        }
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
    
        public boolean intersects(IGameObject actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), 48f, 48f);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), 48f, 48f);
        return objekt.intersects(predmet);
    }

}
