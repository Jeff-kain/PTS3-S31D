/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerup;

import Game.Player;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jeffrey
 */
public class Bomb_Up extends PowerUp{

    public float x;
    public float y;
    public Boolean visible;
    public String name = "Bomb_Up";

    public Bomb_Up(String name, float x, float y, Boolean visible)
    {
        super(name);
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }


}
