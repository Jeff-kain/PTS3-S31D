/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
/**
 *
 * @author jeffrey
 */
public class Bomberman extends BasicGame{

    /**
     * @param args the command line arguments
     */
        // TODO code application logic here
        	public Bomberman(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.drawString("Howdy!", 100, 100);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Bomberman("Simple Slick Game"));
			appgc.setDisplayMode(1960, 1000, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    }
