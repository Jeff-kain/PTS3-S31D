/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import Multiplayer.Client;
import Multiplayer.HostServer;
import Multiplayer.Manager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Menu_StateBasedGame extends BasicGameState {

    private StateBasedGame game;
    Manager manager = Manager.getManager();
    private boolean gamestarted;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.drawString("Bomberman", 50, 10);

        g.drawString("1. Host game", 50, 100);
        g.drawString("2. Join game", 50, 120);
        g.drawString("3. Local game", 50, 140);
        g.drawString("4. Show Lobbies", 50, 160);
        g.drawString("5. Back to portal", 50, 180);

        if (gamestarted == false) {
            g.drawString("Waiting for players", 50, 280);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if (manager.getRemoteclient() != null) {
        }
    }

    @Override
    public int getID() {
        return 0;
    }

    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_1:
                manager.setBoolLAN(true);
                manager.setAmplayer(2);
                manager.setPlayer1(1);
                 {
                    try {
                        HostServer host = new HostServer(1100, "host");
                    } catch (RemoteException ex) {
                        Logger.getLogger(Menu_StateBasedGame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(Menu_StateBasedGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

                }
                break;
            case Input.KEY_2:
                manager.setBoolClient(true);
                manager.setBoolLAN(true);
                manager.setAmplayer(2);
                manager.setPlayer2(2);
                String hostIP = null;
                //hostIP = "145.93.64.173"; //tim IP
                hostIP = "145.93.249.22";
                String hostservice = ("rmi://" + hostIP + ":" + 1100 + "/host");
                 {
                    try {
                        Client client = new Client(1090, "client", hostservice);
                    } catch (RemoteException ex) {
                        Logger.getLogger(Menu_StateBasedGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            case Input.KEY_3:
                // TODO: Implement later
                manager.setBoolClient(false);
                manager.setBoolLAN(false);
                manager.setAmplayer(2);
                manager.setPlayer2(2);
                manager.setPlayer1(1);
                game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;

            case Input.KEY_4:
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            default:
                break;
        }
    }
}
