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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Mouse;

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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;
import powerup.*;

/**
 *
 * @author Rob
 */
public class Game_StateBasedGame extends BasicGameState {

    /**
     * @param args the command line arguments
     */
    private ArrayList<Player> players;
    private HashMap<Bomb, Animation> bombAnimations;
    private Circle mouseBall;
    private List<Bomb> bombs;
    private List<Bomb> bombs2;
    private ArrayList<Box> boxes;
    private TiledMap map;
    private int x;
    private int y;
    private Playground playground;
    private SpriteSheet sprites;
    private SpriteSheet spritesCastle;
    private Image character;
    private float tile;
    private Player player;
    private Player player2;
    private Game game;
    private StateBasedGame game1;
    private float timeOutP1;
    private float timeOutP2;
    private boolean isEnded;
    private static String[] arguments;
    private static AppGameContainer appgc;
    private String WinningTeam;


    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        game = Game.getInstance();
        this.game1 = sbg;
        playground = game.playground();
        map = playground.getMap();
        bombAnimations = new HashMap<>();
        x = 1;
        y = 1;
        tile = 48f;
        isEnded = false;
        //mouseBall = new Circle(72,72,20);
        bombs = new CopyOnWriteArrayList<>();
        bombs2 = new CopyOnWriteArrayList<>();
        sprites = new SpriteSheet("res" + File.separator + "sprites3x.png", 48, 48, Color.decode("#FF00FF"));
        spritesCastle = new SpriteSheet("res" + File.separator + "castlesprites.png", 48, 48, Color.decode("#FF00FF"));
        //character = sprites.getSprite(2, 16);
        game.setTeam1(sprites, TeamColor.BLUE, 48, 624);
        game.setTeam2(sprites, TeamColor.GREEN, 624, 48);
        player = new Player(sprites, 48f, 48f, 1, 48f, false);
        player.setTeamColor(TeamColor.BLUE);
        player2 = new Player(sprites, 624f, 624f, 1, 48f, false);
        player2.setTeamColor(TeamColor.GREEN);
        timeOutP1 = player.getSpeed();
        timeOutP2 = player2.getSpeed();
        loadMap();
        game.getTeam1().addPlayer(player);
        game.getTeam2().addPlayer(player2);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        map.render(0, 0);

        if (game.getTeam1() != null && game.getTeam2() != null) {
            float healthScale = game.getTeam1().currentHealth() / game.getTeam1().maxHealth();
            float healthScale2 = game.getTeam2().currentHealth() / game.getTeam2().maxHealth();

            g.setColor(Color.white);
            g.drawString("Team 1 HP: ", 10.0f, 30.0f);
            g.setColor(Color.green);
            g.fillRect(100.0f, 25.0f, 250f * healthScale, 20.0f);

            g.setColor(Color.white);
            g.drawString("Team 2 HP: ", 400.0f, 30.0f);
            g.setColor(Color.green);
            g.fillRect(490.0f, 25.0f, 250f * healthScale2, 20.0f);

            g.drawImage(game.getTeam1().getSprite(), game.getTeam1().getX(), game.getTeam1().getY());
            g.drawImage(game.getTeam2().getSprite(), game.getTeam2().getX(), game.getTeam2().getY());
        }
//            g.setColor(Color.blue);
//            g.fill(mouseBall);    

        for (Bomb bomb : bombs) {
            //g.drawImage(bomb.getSprite(), bomb.getX(), bomb.getY());
            Animation animation;
            if (bombAnimations.get(bomb) == null) {
                animation = bomb.getAnimation();
                animation.setLooping(false);
                animation.setAutoUpdate(true);
                bombAnimations.put(bomb, animation);
            } else {
                animation = bombAnimations.get(bomb);
            }

            animation.draw(bomb.getX(), bomb.getY());
        }

        for (Bomb bomb : bombs2) {
            //g.drawImage(bomb.getSprite(), bomb.getX(), bomb.getY());
            Animation animation;
            if (bombAnimations.get(bomb) == null) {
                animation = bomb.getAnimation();
                animation.setLooping(false);
                animation.setAutoUpdate(true);
                bombAnimations.put(bomb, animation);
            } else {
                animation = bombAnimations.get(bomb);
            }

            animation.draw(bomb.getX(), bomb.getY());
        }
//        for (Player p : game.getTeam1().getPlayers()) {
//            g.drawImage(p.getSprite(), p.getX(), p.getY());
//        }
//
//        for (Player p : game.getTeam2().getPlayers()) {
//            g.drawImage(p.getSprite(), p.getX(), p.getY());
//        }
        if (player.getVisible() == true) {
            g.drawImage(player.getSprite(), player.getX(), player.getY());
        }
        if (player2.getVisible() == true) {
            g.drawImage(player2.getSprite(), player2.getX(), player2.getY());
        }

        for (PowerUp powerUp : playground.getPowerups()) {
            g.drawImage(powerUp.getSprite(), powerUp.getX(), powerUp.getY());
        }
        for (Box box : playground.getBoxes()) {
            g.drawImage(box.getSprite(), box.getX(), box.getY());
        }

        for (IGameObject o : game.playground().getMapobjects()) {
            g.drawImage(o.getSprite(), o.getX(), o.getY());
        }

        if (game.getTeam1().currentHealth() <= 0) {

            isEnded = true;
//                g.setColor(Color.blue);
//                g.fillRect(220.0f, 220.0f, 270.0f, 230.0f);
//                g.setColor(Color.white);
//                g.drawString("Team2 has won the game!", 250, 250);
//
//                Image play = new Image("res/play.png");
//                Image replay = new Image("res/replay.png");
//                g.drawImage(play, 250, 300);
//                g.drawImage(replay, 380, 300);
            WinningTeam = "Team Blue Wins";
            game1.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

        } else if (game.getTeam2().currentHealth() <= 0) {

            isEnded = true;
            g.setColor(Color.blue);
            g.fillRect(220.0f, 220.0f, 270.0f, 230.0f);
            g.setColor(Color.white);
            g.drawString("Team2 has won the game!", 250, 250);
//
//                Image play = new Image("res/play.png");
//                Image replay = new Image("res/replay.png");
//                g.drawImage(play, 250, 300);
//                g.drawImage(replay, 380, 300);
            WinningTeam = "Team Green Wins";
            game1.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        //team1.damage();
        int objectLayer = map.getLayerIndex("indestructable");
        int posX;
        int posY;
        int pos2Y;
        int pos2X;
        float hpos2x = player2.getX();
        float hpos2y = player2.getY();
        float hposx = player.getX();
        float hposy = player.getY();

        float sensitivity = 1f;

        if (isEnded) {
            posX = Mouse.getX();
            posY = Mouse.getY();

            //Stop 250 / 300
            if ((posX > 250 && posX < 350) && (posY > 300 && posY < 400)) {
                if (Mouse.isButtonDown(0)) {
                    System.exit(0);
                }
            }

            //Replay 380, 300
            if ((posX > 380 && posX < 480) && (posY > 300 && posY < 400)) {
                if (Mouse.isButtonDown(0)) {
                    appgc.reinit();
//                    try {
//                        //main(arguments);
//                        AppGameContainer appgc;
//                        appgc = new AppGameContainer(new Bomberman("Bomberman"));
//                        appgc.start();
//                    } catch (SlickException ex) {
//                        Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
//                   }
                }
            }
        }

        // Player 1 controls
        if (player.getVisible() == true) {
            posX = Math.round(player.getX()) / 48;
            posY = Math.round(player.getY()) / 48;

            if (timeOutP1 <= 0) {
                if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
                    player.reloadSprite(Direction.WEST);

                    if (map.getTileId(posX - 1, posY, objectLayer) == 0) {
                        player.moveLeft();
                    }

                } else if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
                    player.reloadSprite(Direction.EAST);
                    if (map.getTileId(posX + 1, posY, objectLayer) == 0) {
                        player.moveRight();
                    }

                } else if (gc.getInput().isKeyDown(Input.KEY_UP)) {
                    player.reloadSprite(Direction.NORTH);

                    if (map.getTileId(posX, posY - 1, objectLayer) == 0) {
                        player.moveUp();
                    }

                } else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
                    player.reloadSprite(Direction.SOUTH);

                    if (map.getTileId(posX, posY + 1, objectLayer) == 0) {
                        player.moveDown();
                    }
                }

                timeOutP1 = player.getSpeed();
            }

            if (timeOutP1 > 0) {
                timeOutP1 -= delta;
            } else {
                timeOutP1 = 0;
            }

            if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
                Bomb b = new Bomb(sprites, player.getX(), player.getY(), player.getBombRange());

                if (bombs.size() < player.getBombCount()) {
                    player.setKickDirection(Direction.NONE);
                    bombs.add(b);
                }
            }

            if (player.intersectWithWall()) {
                player.setPosition(hposx, hposy);
            }

            if (player.upBomb()) {
                player.setBombCount(player.getBombCount() + 1);
            }
        }
        //player 2 controls
        if (player2.getVisible() == true) {
            pos2X = Math.round(player2.getX()) / 48;
            pos2Y = Math.round(player2.getY()) / 48;

            if (timeOutP2 <= 0) {
                if (gc.getInput().isKeyDown(Input.KEY_A)) {
                    player2.reloadSprite(Direction.WEST);

                    if (map.getTileId(pos2X - 1, pos2Y, objectLayer) == 0) {
                        player2.moveLeft();
                    }

                } else if (gc.getInput().isKeyDown(Input.KEY_D)) {
                    player2.reloadSprite(Direction.EAST);
                    if (map.getTileId(pos2X + 1, pos2Y, objectLayer) == 0) {
                        player2.moveRight();
                    }

                } else if (gc.getInput().isKeyDown(Input.KEY_W)) {
                    player2.reloadSprite(Direction.NORTH);

                    if (map.getTileId(pos2X, pos2Y - 1, objectLayer) == 0) {
                        player2.moveUp();
                    }

                } else if (gc.getInput().isKeyDown(Input.KEY_S)) {
                    player2.reloadSprite(Direction.SOUTH);

                    if (map.getTileId(pos2X, pos2Y + 1, objectLayer) == 0) {
                        player2.moveDown();
                    }
                }

                timeOutP2 = player2.getSpeed();
            }

            if (timeOutP2 > 0) {
                timeOutP2 -= delta;
            } else {
                timeOutP2 = 0;
            }

            if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
                Bomb b = new Bomb(sprites, player2.getX(), player2.getY(), player2.getBombRange());

                if (bombs2.size() < player2.getBombCount()) {
                    player2.setKickDirection(Direction.NONE);
                    bombs2.add(b);
                }
            }

            if (player2.intersectWithWall()) {
                player2.setPosition(hpos2x, hpos2y);
            }

            if (player2.upBomb()) {
                player2.setBombCount(player2.getBombCount() + 1);
            }
        }

        player.Update();
        player2.Update();

        for (Bomb b : bombs2) {
            // player 1 bomb collision
            if (b.intersects(player) && player.getKick() == false) {
                player.setPosition(hposx, hposy);

            }
            if (b.intersects(player) && player.getKick() == true) {

                // TODO KICK A BOMB
            }
            //player 2 bomb collision
            if (b.intersects(player2) && player2.getKick() == false) {
                player2.setPosition(hpos2x, hpos2y);

            }
            if (b.intersects(player2) && player2.getKick() == true) {

                // TODO KICK A BOMB
            }
        }
        for (Bomb b : bombs) {
            // player 2 bomb collision
            if (b.intersects(player2) && player2.getKick() == false) {
                player2.setPosition(hpos2x, hpos2y);

            }
            if (b.intersects(player2) && player2.getKick() == true) {

                // TODO KICK A BOMB
            }
            // player 1 bomb collision
            if (b.intersects(player) && player.getKick() == false) {
                player.setPosition(hposx, hposy);

            }
            if (b.intersects(player) && player.getKick() == true) {

                // TODO KICK A BOMB
            }
        }
        if (bombs != null) {
            for (Bomb b : bombs) {
                if (b.isExploded()) {
                    bombs.remove(b);
                } else {
                    b.Update();
                }
            }
        }

        if (bombs2 != null) {
            for (Bomb b : bombs2) {
                if (b.isExploded()) {
                    bombs2.remove(b);
                } else {
                    b.Update();
                }
            }
        }
        for (IGameObject o : game.playground().getMapobjects()) {
            o.Update();
        }
    }
    
    public void loadMap() throws SlickException {
        int objectCount = map.getObjectCount(0);
        float X;
        float Y;
        for (int i = 0; i < objectCount; i++) {
            switch (map.getObjectType(0, i)) {
                case "Box":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    Box b = new Box(sprites, (float) X, (float) Y);
                    playground.addBox(b);
                    break;
                case "Player":
                    break;
                case "BlueCastle":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    game.setTeam1(spritesCastle, TeamColor.BLUE, (float) X, (float) Y);
                    break;
                case "RedCastle":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    game.setTeam2(spritesCastle, TeamColor.GREEN, (float) X, (float) Y);
                    break;
                case "Bomb_Up":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    PowerUp p = new Bomb_Up(sprites, "Bomb_Up", (float) X, (float) Y, false);
                    playground.addPowerup(p);
                    break;
                case "Explosion_Up":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    PowerUp p1 = new Explosion_Up(sprites, "Explosion_Up", (float) X, (float) Y, false);
                    playground.addPowerup(p1);
                    break;
                case "Kick":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    PowerUp p2 = new Kick(sprites, "Kick", (float) X, (float) Y, false);
                    playground.addPowerup(p2);
                    break;
                case "Speed_Up":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    PowerUp p3 = new Speed_Up(sprites, "Speed_Up", (float) X, (float) Y, false);
                    playground.addPowerup(p3);
                    break;
                case "PowerUp":
                    X = map.getObjectX(0, i);
                    Y = map.getObjectY(0, i);
                    PowerUp p4 = new PowerUp("Speed_Up", (float) X, (float) Y);//niks doen
                    break;
            }
        }
    }
}
