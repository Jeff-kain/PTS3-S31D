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
 * @author jeffrey
 */
public class Connection_Lost extends BasicGameState {

    private StateBasedGame Game;

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        sbg = Game;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("Connection lost", 50, 50);
        g.drawString("1. Close Game", 50, 100);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
    }

    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_1:
                System.exit(1);
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
