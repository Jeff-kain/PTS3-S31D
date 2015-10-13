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
public class Box implements IGameObject{
    
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    private Image sprite;
    private SpriteSheet sprites;
    private Float x;
    private Float y;

    public Box(SpriteSheet sprites, Float x, Float y) {
        this.sprites = sprites;
        this.x = x;
        this.y = y;
        
        this.sprite = this.sprites.getSubImage(9, 13);
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
