/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author tverv
 */
public class Bomb implements IGameObject{
    private Image sprite;
    private SpriteSheet sprites;
    private Float x;
    private Float y;
    private int explodeTime;
    private Animation explodingBomb;
    private boolean exploded;
    private boolean intersectWithPlayer;
    private final static int STEP = 2;
    private int steps;

    public Bomb(SpriteSheet sprites, Float x, Float y) {
        this.sprites = sprites;
        this.x = x;
        this.y = y;
        this.explodeTime = 200;
        this.sprite = this.sprites.getSubImage(11, 15);
    }

    public Animation getAnimation() {
        int[] frames = {4,18,5,18,6,18,7,18,8,18,9,18};
        int[] durations = {100,100,100,100,100,100};
        Animation animation = new Animation(sprites,frames,durations);

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
}
