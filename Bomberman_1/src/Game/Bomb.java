/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

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
    private Game game = new Game();

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
        int[] durations = {100, 100, 100, 100, 100, 100};
        Animation animation = new Animation(sprites, frames, durations);

        return animation;
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
                    } else {
                        Explosion flame = new Explosion(sprites, Direction.EAST);
                        flame.setPosition(oldX + 48 + 48 * r, oldY);
                        game.playground().addToLevel(flame);
                        checkForBox(this.getX(), this.getY());
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
                    } else {
                        Explosion flame = new Explosion(sprites, Direction.WEST);
                        flame.setPosition(oldX - 48 - 48 * r, oldY);
                        game.playground().addToLevel(flame);
                        checkForBox(this.getX(), this.getY());
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
                    } else {
                        Explosion flame = new Explosion(sprites, Direction.SOUTH);
                        flame.setPosition(oldX, oldY + 48 + 48 * r);
                        game.playground().addToLevel(flame);
                        checkForBox(this.getX(), this.getY());
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
                    } else {
                        Explosion flame = new Explosion(sprites, Direction.NORTH);
                        flame.setPosition(oldX, oldY - 48 - 48 * r);
                        game.playground().addToLevel(flame);
                        checkForBox(this.getX(), this.getY());
                        this.y += (48 + 48 * r);
                        break;
                    }
                    this.y += (48 + 48 * r);
                }
                break;
        }

    }

    public void checkForBox(float x, float y) {
        for (Box o : game.playground().getBoxes()) {
            if (o.getX() == x && o.getY() == y) {
                game.playground().RemoveBox(o);
            }
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

}
