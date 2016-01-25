/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.Serializable;
import org.newdawn.slick.Image;

/**
 *
 * @author jeffrey
 */
public interface IGameObject extends Serializable {

    public Game game = Game.getInstance();

    public Image getSprite();

    public Float getX();

    public Float getY();

    public void Update();

    public boolean intersects(IGameObject actor);

}
