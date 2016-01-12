/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import Game.Game;
import Game.Bomb;
import Game.Box;
import Game.Player;
import Game.Playground;
import Game.Team;
import Game.TeamColor;
import Game.Direction;
import Game.IGameObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.tiled.TiledMap;
import powerup.*;

/**
 *
 * @author jeffrey //
 */
public class Bomberman {

    public static String[] arg = new String[2];

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            arg[i] = args[i];
            System.out.println(args[i]);
        }
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Bomberman_StateBasedGame("Bomberman"));
            appgc.setDisplayMode(720, 720, false);
            appgc.setVSync(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
