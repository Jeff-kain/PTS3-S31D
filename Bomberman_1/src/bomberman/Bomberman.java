/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import Game.Team;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
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
        // TODO code application logic here
        public Bomberman(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
        team1 = new Team();
        team2 = new Team();
        }

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
           //team1.damage();
        }

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
            
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
			appgc = new AppGameContainer(new Bomberman("Simple Slick Game"));
			appgc.setDisplayMode(1080, 760, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    }
