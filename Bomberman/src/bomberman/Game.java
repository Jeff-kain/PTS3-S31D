/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import game.Level;
import game.MapObjects;
import game.actor.Actors;
import game.actor.Player;
import game.actor.enemies.Dragon;
import game.score.BestScore;
import game.score.Score;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Michal
 */
public class Game extends BasicGame {

    /**
     *  enumerated type for game states
     */
    public static enum GameState {

        /**
         *
         */
        PLAYING,
        /**
         *
         */
        PAUSED,
        /**
         *
         */
        FAILED,
        /**
         *
         */
        FINISHED
    }
    private Player hrac;
    private TextField text;
    private TextField text2;
    private Level level;
    private int playingTime;
    private Score stat;
    private BestScore bestScore = new BestScore();
    private final String levelName = "level";
    private int dyingTime;

    /**
     *  constructor for main slick game class
     */
    public Game() {
        super("Bomberman");
        stat = Score.getScore();
        dyingTime = 150;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        level = Level.getLevel();
        level.loadLevel(levelName + level.getLevelNumber());
        hrac = level.getPlayer();
        TrueTypeFont font = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 14), true);
        text = new TextField(gc, font, 60, 200, 500, 25);
        text2 = new TextField(gc, font, 270, 230, 110, 25);

    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            gc.exit();
        }
        if (input.isKeyPressed(Input.KEY_P)) {
            if (level.getGameState() != GameState.PAUSED) {
                level.setGameState(GameState.PAUSED);
                playingTime = stat.getPlayingTime();
            } else {
                level.setGameState(GameState.PLAYING);
            }
        }
        if (level.getGameState() == GameState.FAILED) {
            dyingTime--;
            if (dyingTime == 0) {
                gc.exit();
            }
        }

        if (level.getGameState() == GameState.FINISHED) {
            if (!hrac.isStopTime() && level.getLevelNumber() < 5) {
                playingTime = stat.getPlayingTime();
                bestScore.addScore(stat);
                int minutes = playingTime / 60;
                int seconds = playingTime % 60;
                text.setText("  Playing time: " + minutes + " min " + seconds + "secs" + "   Enemies killed: " + stat.getEnemiesKilled() + "   Walls Destroyed: " + stat.getWallsDestroyed());
                text2.setText("  Press Enter");
            }
            hrac.setStopTime(true);
            if (input.isKeyPressed(Input.KEY_ENTER)) {
                hrac.setStopTime(false);
                level.incLevel();
                if (level.getLevelNumber() == 5) {
                    System.exit(0);
                }
                level.reloadLevel();
                stat.restart();
                init(gc);
            }

        }
        if (level.getGameState() == GameState.PLAYING) {
            for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
                MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
                if (o instanceof Actors) {
                    ((Actors) o).act();
                }
            }

        }
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        level.show();
        for (MapObjects o : level.getListOfObjects()) {
            if (o instanceof Dragon) {
                for (Dragon d : Dragon.getBody()) {
                    d.getAnimation().draw(d.getX(), d.getY());
                }
            } else {
                o.getAnimation().draw(o.getX(), o.getY());
            }
        }
        level.showWalls();
        if (level.getLevelNumber() == 4) {
            grphcs.setColor(Color.green);
            grphcs.drawString("Final level", 270, 200);
        }


        if (level.getGameState() == GameState.FINISHED && level.getLevelNumber() < 4) {
            grphcs.setColor(Color.green);
            grphcs.drawString("Level " + level.getLevelNumber() + " completed", 240, 8);
            grphcs.setColor(Color.white);
            text.render(gc, grphcs);
            text2.render(gc, grphcs);
        }
        if (level.getGameState() == GameState.FAILED) {
            grphcs.setColor(Color.red);
            grphcs.drawString("You died", 270, 8);
        }
        if (level.getGameState() == GameState.PAUSED) {
            int minutes = playingTime / 60;
            int seconds = playingTime % 60;
            grphcs.drawString("Actual playing time: " + minutes + " min " + seconds + " secs", 180, 8);

        }
    }
}
