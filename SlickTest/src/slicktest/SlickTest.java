/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slicktest;

import java.util.ArrayList;
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

/**
 *
 * @author tverv
 */
public class SlickTest extends BasicGame{

    private Circle mouseBall;
    private ArrayList<Circle> bombs;
    
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
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        } 
        
        catch (SlickException ex) {
            Logger.getLogger(SlickTest.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        mouseBall = new Circle(200,200,10);
        bombs = new ArrayList<>();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        float sensitivity = 0.5f;
        
        if(gc.getInput().isKeyDown(Input.KEY_LEFT)){
            if(mouseBall.getCenterX() != 0){
                mouseBall.setCenterX(mouseBall.getCenterX() - sensitivity);
            }
        }
        
        if(gc.getInput().isKeyDown(Input.KEY_RIGHT)){
            if(mouseBall.getCenterX() != gc.getWidth()){
                mouseBall.setCenterX(mouseBall.getCenterX() + sensitivity);
            }
        }
        
        if(gc.getInput().isKeyDown(Input.KEY_UP)){
            if(mouseBall.getCenterY() != 0){
                mouseBall.setCenterY(mouseBall.getCenterY() - sensitivity);
            }
        }
        
        if(gc.getInput().isKeyDown(Input.KEY_DOWN)){
            if(mouseBall.getCenterY() != gc.getHeight()){
                mouseBall.setCenterY(mouseBall.getCenterY() + sensitivity);
            }
        }
        
        if(gc.getInput().isKeyPressed(Input.KEY_SPACE)){
            Circle bomb = new Circle(mouseBall.getCenterX(), mouseBall.getCenterY(), 5);
            bombs.add(bomb);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.blue);
        g.fill(mouseBall);    
        
        for(Circle bomb:bombs){
            g.setColor(Color.red);
            g.fill(bomb);
        }
    }
    
}
