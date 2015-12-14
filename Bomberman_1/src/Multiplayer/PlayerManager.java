/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Game.Direction;
import Game.Player;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jeffrey
 */
public class PlayerManager implements Serializable {

    public ArrayList<Player> playerlist;

    public PlayerManager() {
        playerlist = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayerlist() {
        return playerlist;
    }

    public void movePlayers() {
        for (int i = 0; i < playerlist.size(); i++) {
            playerlist.get(i).move();
        }
    }

    public void updatePlayers(int keycode, boolean pressed) {
        // System.out.println("ArrayListsize " + PlayerList.size());
        for (int i = 0; i < playerlist.size(); i++) {

            playerlist.get(i).update(keycode, pressed);
        }

    }
}
