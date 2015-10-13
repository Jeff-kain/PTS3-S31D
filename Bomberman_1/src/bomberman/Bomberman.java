/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import Game.Bomb;
import Game.Box;
import Game.Playground;
import Game.Team;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
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
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.tiled.TiledMap;
/**
 *
 * @author jeffrey
 */
public class Bomberman extends BasicGame{

    /**
     * @param args the command line arguments
     */
    
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
         
        // TODO code application logic here
        public Bomberman(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
            team1 = new Team();
            team2 = new Team();
            playground = new Playground();
            x = 1;
            y = 1;
            mouseBall = new Circle(72,72,20);
            bombs = new ArrayList<>();
            boxes = new ArrayList<>();
            map = playground.getMap();
            sprites = new SpriteSheet("res" + File.separator + "sprites3xt.png", 48, 48);
            character = sprites.getSprite(2, 16);
            
            Box b = new Box(sprites, 500f,200f);
            boxes.add(b);
        }

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
           //team1.damage();
            int objectLayer = map.getLayerIndex("indestructable");
            int posX;
            int posY;

            float sensitivity = 1f;

            if(gc.getInput().isKeyPressed(Input.KEY_LEFT)){
                posX = Math.round(mouseBall.getX()) / 48;
                posY = Math.round(mouseBall.getY()) / 48;
                System.out.println("x: " + posX + " y: " + posY);

                if(map.getTileId(posX - 1, posY, objectLayer)==0){
                    mouseBall.setCenterX(mouseBall.getCenterX() - sensitivity * 48);
                }
            }

            if(gc.getInput().isKeyPressed(Input.KEY_RIGHT)){
                posX = Math.round(mouseBall.getX()) / 48;
                posY = Math.round(mouseBall.getY()) / 48;
                System.out.println("x: " + posX + " y: " + posY);

                if(map.getTileId(posX + 1, posY, objectLayer)==0){
                    mouseBall.setCenterX(mouseBall.getCenterX() + sensitivity * 48);
                    Timer t = new Timer();
                }
            }

            if(gc.getInput().isKeyPressed(Input.KEY_UP)){
                posX = Math.round(mouseBall.getX()) / 48;
                posY = Math.round(mouseBall.getY()) / 48;
                System.out.println("x: " + posX + " y: " + posY);

                if(map.getTileId(posX, posY - 1, objectLayer)==0){
                    mouseBall.setCenterY(mouseBall.getCenterY() - sensitivity * 48);
                }
            }

            if(gc.getInput().isKeyPressed(Input.KEY_DOWN)){
                posX = Math.round(mouseBall.getX()) / 48;
                posY = Math.round(mouseBall.getY()) / 48;
                System.out.println("x: " + posX + " y: " + posY);   

                if(map.getTileId(posX, posY + 1, objectLayer)==0){
                    mouseBall.setCenterY(mouseBall.getCenterY() + sensitivity * 48);
                }
            }

            if(gc.getInput().isKeyPressed(Input.KEY_SPACE)){
                Bomb b = new Bomb(sprites, mouseBall.getX(), mouseBall.getY());
                bombs.add(b);
            }
        }

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
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

            g.setColor(Color.blue);
            g.fill(mouseBall);    
            
            g.drawImage(character, 48, 48);
        
            for(Bomb bomb:bombs){
                g.drawImage(bomb.getSprite(), bomb.getX(), bomb.getY());
        }
            for(Box box: boxes)
            {
                g.drawImage(box.getSprite(), box.getX(), box.getY());
            }
            //g.drawString("Howdy!", 100, 100);
    //        g.setColor(Color.green);
    //        g.fillRect(20.0f, 10.0f, 300.0f, 20.0f);
    //        
    //        g.setColor(Color.blue);
    //        g.fillRect(20.0f, 10.0f, team1.maxHealth() / team1.currentHealth() * 300.0f, 20.0f);
	}
        
            

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Bomberman("Bomberman"));
			appgc.setDisplayMode(720, 720, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    }