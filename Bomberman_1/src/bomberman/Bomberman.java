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
import org.newdawn.slick.tiled.TiledMap;
import powerup.*;

/**
 *
 * @author jeffrey
 */
public class Bomberman extends BasicGame {

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
    private float timeOutP1;
    private float timeOutP2;
    private boolean isEnded;
    private static String[] arguments;
    private static AppGameContainer appgc;

    //private AppGameContainer appgc;
    // TODO code application logic here
    public Bomberman(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        game = Game.getInstance();
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
    public void update(GameContainer gc, int delta) throws SlickException {
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
            if (timeOutP1 <= 0) {
                if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
                    player.reloadSprite(Direction.WEST);
                    posX = Math.round(player.getX()) / 48;
                    posY = Math.round(player.getY()) / 48;
                    //System.out.println("x: " + posX + " y: " + posY);

                    if (map.getTileId(posX - 1, posY, objectLayer) == 0) {
                        //player.setX(player.getX() - sensitivity * 48);
                        player.moveLeft();
                    }
                    timeOutP1 = player.getSpeed();
                } else if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
                    player.reloadSprite(Direction.EAST);
                    posX = Math.round(player.getX()) / 48;
                    posY = Math.round(player.getY()) / 48;
                    //System.out.println("x: " + posX + " y: " + posY);
                    //System.out.print(hposx);
                    if (map.getTileId(posX + 1, posY, objectLayer) == 0) {
                        //player.setX(player.getX() + sensitivity * 48);
                        player.moveRight();
                    }
                    timeOutP1 = player.getSpeed();
                } else if (gc.getInput().isKeyDown(Input.KEY_UP)) {
                    player.reloadSprite(Direction.NORTH);
                    posX = Math.round(player.getX()) / 48;
                    posY = Math.round(player.getY()) / 48;
                    //System.out.println("x: " + posX + " y: " + posY);

                    if (map.getTileId(posX, posY - 1, objectLayer) == 0) {
                        // player.setY(player.getY() - sensitivity * 48);
                        player.moveUp();
                    }
                    timeOutP1 = player.getSpeed();
                } else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
                    player.reloadSprite(Direction.SOUTH);
                    posX = Math.round(player.getX()) / 48;
                    posY = Math.round(player.getY()) / 48;

                    if (map.getTileId(posX, posY + 1, objectLayer) == 0) {
                        player.moveDown();
                    }
                    timeOutP1 = player.getSpeed();
                }

            } else {
                //System.out.println(timeOut);

                if (timeOutP1 > 0) {
                    timeOutP1 -= delta;
                } else {
                    timeOutP1 = 0;
                }

            }

            if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
                Bomb b = new Bomb(sprites, player.getX(), player.getY(), player.getBombRange());

                if (bombs.size() < player.getBombCount()) {
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
            if (timeOutP2 <= 0) {
                if (gc.getInput().isKeyDown(Input.KEY_A)) {
                    player2.reloadSprite(Direction.WEST);
                    pos2X = Math.round(player2.getX()) / 48;
                    pos2Y = Math.round(player2.getY()) / 48;
                    //System.out.println("x: " + posX + " y: " + posY);

                    if (map.getTileId(pos2X - 1, pos2Y, objectLayer) == 0) {
                        //player.setX(player.getX() - sensitivity * 48);
                        player2.moveLeft();
                    }
                    timeOutP2 = player2.getSpeed();
                } else if (gc.getInput().isKeyDown(Input.KEY_D)) {
                    player2.reloadSprite(Direction.EAST);
                    pos2X = Math.round(player2.getX()) / 48;
                    pos2Y = Math.round(player2.getY()) / 48;
                    //System.out.println("x: " + posX + " y: " + posY);
                    //System.out.print(hposx);
                    if (map.getTileId(pos2X + 1, pos2Y, objectLayer) == 0) {
                        //player.setX(player.getX() + sensitivity * 48);
                        player2.moveRight();
                    }
                    timeOutP2 = player2.getSpeed();
                } else if (gc.getInput().isKeyDown(Input.KEY_W)) {
                    player2.reloadSprite(Direction.NORTH);
                    pos2X = Math.round(player2.getX()) / 48;
                    pos2Y = Math.round(player2.getY()) / 48;
                    //System.out.println("x: " + posX + " y: " + posY);

                    if (map.getTileId(pos2X, pos2Y - 1, objectLayer) == 0) {
                        // player.setY(player.getY() - sensitivity * 48);
                        player2.moveUp();
                    }
                    timeOutP2 = player2.getSpeed();
                } else if (gc.getInput().isKeyDown(Input.KEY_S)) {
                    player2.reloadSprite(Direction.SOUTH);
                    pos2X = Math.round(player2.getX()) / 48;
                    pos2Y = Math.round(player2.getY()) / 48;

                    if (map.getTileId(pos2X, pos2Y + 1, objectLayer) == 0) {
                        player2.moveDown();
                    }
                    timeOutP2 = player2.getSpeed();
                }

            } else {
                //System.out.println(timeOut);

                if (timeOutP2 > 0) {
                    timeOutP2 -= delta;
                } else {
                    timeOutP2 = 0;
                }

            }

            if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
                Bomb b = new Bomb(sprites, player2.getX(), player2.getY(), player2.getBombRange());

                if (bombs2.size() < player2.getBombCount()) {
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

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
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
            game.getTeam1().setCurrentHealth(0);
            isEnded = true;
            g.setColor(Color.blue);
            //g.fillRect(220.0f, 220.0f, 270.0f, 230.0f);
            g.setColor(Color.white);
            g.drawString("Team2 has won the game!", 250, 250);

            Image play = new Image("res/play.png");
            Image replay = new Image("res/replay.png");
            g.drawImage(play, 250, 300);
            g.drawImage(replay, 380, 300);
        } else if (game.getTeam2().currentHealth() <= 0) {
            game.getTeam2().setCurrentHealth(0f);
            isEnded = true;
            g.setColor(Color.blue);
            g.fillRect(220.0f, 220.0f, 270.0f, 230.0f);
            g.setColor(Color.white);
            g.drawString("Team2 has won the game!", 250, 250);

            Image play = new Image("res/play.png");
            Image replay = new Image("res/replay.png");
            g.drawImage(play, 250, 300);
            g.drawImage(replay, 380, 300);
        }
        //g.drawString("Howdy!", 100, 100);
        //        g.setColor(Color.green);
        //        g.fillRect(20.0f, 10.0f, 300.0f, 20.0f);
        //        
        //        g.setColor(Color.blue);
        //        g.fillRect(20.0f, 10.0f, team1.maxHealth() / team1.currentHealth() * 300.0f, 20.0f);
    }

    public static void main(String[] args) {
        try {
            arguments = args;
            //AppGameContainer appgc;
            appgc = new AppGameContainer(new Bomberman("Bomberman"));
            appgc.setDisplayMode(720, 720, false);
            appgc.setVSync(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
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
//                case "Kick":
//                    X = map.getObjectX(0, i);
//                    Y = map.getObjectY(0, i);
//                    PowerUp p2 = new Kick(sprites, "Kick", (float) X, (float) Y, false);
//                    playground.addPowerup(p2);
//                    break;
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
