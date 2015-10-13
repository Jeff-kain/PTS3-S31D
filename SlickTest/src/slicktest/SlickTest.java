/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slicktest;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author tverv
 */
public class SlickTest extends BasicGame{

    private Circle mouseBall;
    private ArrayList<Circle> bombs;
    private TiledMap map;
    private int x;
    private int y;
    private final String path = "res" + File.separator + "map.tmx";
    
    public SlickTest(String gameName) {
        super(gameName);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SlickTest("SlickTest"));
            appgc.setDisplayMode(480, 480, false);
            appgc.start();
        } 
        
        catch (SlickException ex) {
            Logger.getLogger(SlickTest.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        x = 1;
        y = 1;
        mouseBall = new Circle(24,24,6);
        bombs = new ArrayList<>();
        map = new TiledMap(path);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        int objectLayer = map.getLayerIndex("objects");
        int posX;
        int posY;
        
        float sensitivity = 1f;
        
        if(gc.getInput().isKeyPressed(Input.KEY_LEFT)){
            posX = Math.round(mouseBall.getX()) / 16;
            posY = Math.round(mouseBall.getY()) / 16;
            System.out.println("x: " + posX + " y: " + posY);
            
            if(map.getTileId(posX - 1, posY, objectLayer)==0){
                mouseBall.setCenterX(mouseBall.getCenterX() - sensitivity * 16);
            }
        }
        
        if(gc.getInput().isKeyPressed(Input.KEY_RIGHT)){
            posX = Math.round(mouseBall.getX()) / 16;
            posY = Math.round(mouseBall.getY()) / 16;
            System.out.println("x: " + posX + " y: " + posY);
            
            if(map.getTileId(posX + 1, posY, objectLayer)==0){
                mouseBall.setCenterX(mouseBall.getCenterX() + sensitivity * 16);
                Timer t = new Timer();
            }
        }
        
        if(gc.getInput().isKeyPressed(Input.KEY_UP)){
            posX = Math.round(mouseBall.getX()) / 16;
            posY = Math.round(mouseBall.getY()) / 16;
            System.out.println("x: " + posX + " y: " + posY);
            
            if(map.getTileId(posX, posY - 1, objectLayer)==0){
                mouseBall.setCenterY(mouseBall.getCenterY() - sensitivity * 16);
            }
        }
        
        if(gc.getInput().isKeyPressed(Input.KEY_DOWN)){
            posX = Math.round(mouseBall.getX()) / 16;
            posY = Math.round(mouseBall.getY()) / 16;
            System.out.println("x: " + posX + " y: " + posY);   
            
            if(map.getTileId(posX, posY + 1, objectLayer)==0){
                mouseBall.setCenterY(mouseBall.getCenterY() + sensitivity * 16);
            }
        }
        
        if(gc.getInput().isKeyPressed(Input.KEY_SPACE)){
            Circle bomb = new Circle(mouseBall.getCenterX(), mouseBall.getCenterY(), 5);
            bombs.add(bomb);
        }
        
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.scale(2,2);
        
        map.render(0, 0);
        
        g.setColor(Color.blue);
        g.fill(mouseBall);    
        
        for(Circle bomb:bombs){
            g.setColor(Color.red);
            g.fill(bomb);
        }
    }
    
}
