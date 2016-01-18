/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import Game.Bomb;
import Game.Box;
import Game.Direction;
import Game.Game;
import Game.IGameObject;
import Game.Player;
import Game.Playground;
import Game.TeamColor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import powerup.Bomb_Up;
import powerup.Explosion_Up;
import powerup.Kick;
import powerup.PowerUp;
import powerup.Speed_Up;

/**
 *
 * @author Rob
 */
public class Bomberman_StateBasedGame extends StateBasedGame {

    public static String[] arguments;

    public Bomberman_StateBasedGame(String name, String[] args) {
        super(name);
        this.arguments = args;
        for (int i = 0; i < arguments.length; i++) {
//            modus = args[0];
//            arg[i] = args[i];
            System.out.println(arguments[i]);
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new Menu_StateBasedGame(arguments)); // ID = 0
        addState(new Lobby_StateBasedGame()); //ID = 1
        addState(new Game_StateBasedGame()); // ID = 2
        addState(new GameOver_StateBasedGame()); // ID = 3
    }

}
