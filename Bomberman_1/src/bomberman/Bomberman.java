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

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author jeffrey
 */
public class Bomberman extends BasicGame {

    /**
     * @param args the command line arguments
     */
    private ArrayList<Player> players;
    private Team team1;
    private Team team2;
    private Circle mouseBall;
    private ArrayList<Bomb> bombs;
    private ArrayList<Box> boxes;
    private TiledMap map;
    private int x;
    private int y;
    private Playground playground;
    private SpriteSheet sprites;
    private Image character;
    private float tile;
    private Player player;
    private Game game;

    // TODO code application logic here
    public Bomberman(String gamename)
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        game = new Game();
        playground = game.playground();
        map = playground.getMap();
        team1 = new Team(TeamColor.BLUE);
        team2 = new Team(TeamColor.GREEN);
        x = 1;
        y = 1;
        tile = 48f;
        //mouseBall = new Circle(72,72,20);
        bombs = new ArrayList<>();
        sprites = new SpriteSheet("res" + File.separator + "sprites3x.png", 48, 48, Color.decode("#FF00FF"));
        //character = sprites.getSprite(2, 16);
        player = new Player(sprites, 48f, 48f);
        player.setTeamColor(TeamColor.GREEN);
        loadMap();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        //team1.damage();
        int objectLayer = map.getLayerIndex("indestructable");
        int posX;
        int posY;
        float hposx = player.getX();
        float hposy = player.getY();

        float sensitivity = 1f;

        if (gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
            player.reloadSprite(Direction.WEST);
            posX = Math.round(player.getX()) / 48;
            posY = Math.round(player.getY()) / 48;
            //System.out.println("x: " + posX + " y: " + posY);

            if (map.getTileId(posX - 1, posY, objectLayer) == 0) {
                //player.setX(player.getX() - sensitivity * 48);
                player.moveLeft();
            }
        }

        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
            player.reloadSprite(Direction.EAST);
            posX = Math.round(player.getX()) / 48;
            posY = Math.round(player.getY()) / 48;
            //System.out.println("x: " + posX + " y: " + posY);
            //System.out.print(hposx);
            if (map.getTileId(posX + 1, posY, objectLayer) == 0) {
                //player.setX(player.getX() + sensitivity * 48);
                player.moveRight();
            }
        }

        if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
            player.reloadSprite(Direction.NORTH);
            posX = Math.round(player.getX()) / 48;
            posY = Math.round(player.getY()) / 48;
            //System.out.println("x: " + posX + " y: " + posY);

            if (map.getTileId(posX, posY - 1, objectLayer) == 0) {
                // player.setY(player.getY() - sensitivity * 48);
                player.moveUp();
            }
        }

        if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            player.reloadSprite(Direction.SOUTH);
            posX = Math.round(player.getX()) / 48;
            posY = Math.round(player.getY()) / 48;
            //System.out.println("x: " + posX + " y: " + posY);

            if (map.getTileId(posX, posY + 1, objectLayer) == 0) {
                //player.setY(player.getY() + sensitivity * 48);
                player.moveDown();
            }

        }

        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
            Bomb b = new Bomb(sprites, player.getX(), player.getY());
            bombs.add(b);
        }

        if (player.intersectWithWall()) {
            player.setPosition(hposx, hposy);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        map.render(0, 0);

        float healthScale = team1.currentHealth() / team1.maxHealth();
        float healthScale2 = team2.currentHealth() / team2.maxHealth();

        g.setColor(Color.white);
        g.drawString("Team 1 HP: ", 10.0f, 30.0f);
        g.setColor(Color.green);
        g.fillRect(100.0f, 30.0f, 250f * healthScale, 20.0f);

        g.setColor(Color.white);
        g.drawString("Team 2 HP: ", 760.0f, 30.0f);
        g.setColor(Color.green);
        g.fillRect(850.0f, 30.0f, 250f * healthScale2, 20.0f);

//            g.setColor(Color.blue);
//            g.fill(mouseBall);    
        for (Bomb bomb : bombs) {
            g.drawImage(bomb.getSprite(), bomb.getX(), bomb.getY());
        }
        g.drawImage(player.getSprite(), player.getX(), player.getY());

        for (Box box : playground.getBoxes()) {
            g.drawImage(box.getSprite(), box.getX(), box.getY());
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
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Bomberman("Bomberman"));
            appgc.setDisplayMode(720, 720, false);
            appgc.setVSync(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadMap() {
        int objectCount = map.getObjectCount(0);
        for (int i = 0; i < objectCount; i++) {
            switch (map.getObjectType(0, i)) {
                case "Box":
                    float boxX;
                    float boxY;
                    boxX = map.getObjectX(0, i);
                    boxY = map.getObjectY(0, i);
                    Box b = new Box(sprites, (float) boxX, (float) boxY);
                    playground.addBox(b);
                    break;
                case "Player":
                    break;
            }
        }
    }

}
