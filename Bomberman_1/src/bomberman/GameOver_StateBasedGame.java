/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Rob
 */
public class GameOver_StateBasedGame extends BasicGameState {
    
    private StateBasedGame Game;
    
    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        sbg = Game;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.drawString("Game over", 50, 50);
        g.drawString("1. Back to portal", 50, 100);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        
    }
    
    public void keyReleased(int key, char c) {
        switch(key) {
        case Input.KEY_1:
            Game.enterState(0, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            break;
        case Input.KEY_2:
            break;
        case Input.KEY_3:
            // TODO: Implement later
            break;
        default:
            break;
        }
    }
    
}
