/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Multiplayer.Manager;
import java.awt.event.KeyEvent;
import java.io.Serializable;

/**
 *
 * @author jeffrey
 */
public class Keyset implements Serializable {

    public int KeyUp, KeyDown, KeyLeft, KeyRight, KeyBomb;

    public static final int REMUP = -1, REMLEFT = -2, REMDOWN = -3,
            REMRIGHT = -4, REMBOMB = -5;

    Manager manager = Manager.getManager();

    public Keyset(int KeyUp, int KeyDown, int KeyLeft, int KeyRight, int KeyBomb) {
        this.setKeyUp(KeyUp);
        this.setKeyLeft(KeyLeft);
        this.setKeyDown(KeyDown);
        this.setKeyRight(KeyRight);
        this.setKeyBomb(KeyBomb);

    }

    public Keyset(int presetnumber) {

        if (presetnumber == 1) {

            setKeyUp(KeyEvent.VK_W);
            setKeyLeft(KeyEvent.VK_A);
            setKeyDown(KeyEvent.VK_S);
            setKeyRight(KeyEvent.VK_D);
            setKeyBomb(KeyEvent.VK_SPACE);

        } else if (presetnumber == 2) {

            setKeyUp(KeyEvent.VK_UP);
            setKeyLeft(KeyEvent.VK_LEFT);
            setKeyDown(KeyEvent.VK_DOWN);
            setKeyRight(KeyEvent.VK_RIGHT);
            setKeyBomb(KeyEvent.VK_ENTER);

        } 
        else if (presetnumber == -1) {
            setKeyUp(REMUP);
            setKeyLeft(REMLEFT);
            setKeyDown(REMDOWN);
            setKeyRight(REMRIGHT);
            setKeyBomb(REMBOMB);
        } 

        else // if (presetnumber == 3)
        {// ---------------And so on...
            setKeyUp(KeyEvent.VK_I);
            setKeyLeft(KeyEvent.VK_J);
            setKeyDown(KeyEvent.VK_K);
            setKeyRight(KeyEvent.VK_L);
            setKeyBomb(KeyEvent.VK_COMMA);
        }

    }

    public int getKeyUp() {
        return KeyUp;
    }

    public void setKeyUp(int KeyUp) {
        this.KeyUp = KeyUp;
    }

    public int getKeyDown() {
        return KeyDown;
    }

    public void setKeyDown(int KeyDown) {
        this.KeyDown = KeyDown;
    }

    public int getKeyLeft() {
        return KeyLeft;
    }

    public void setKeyLeft(int KeyLeft) {
        this.KeyLeft = KeyLeft;
    }

    public int getKeyRight() {
        return KeyRight;
    }

    public void setKeyRight(int KeyRight) {
        this.KeyRight = KeyRight;
    }

    public int getKeyBomb() {
        return KeyBomb;
    }

    public void setKeyBomb(int KeyBomb) {
        this.KeyBomb = KeyBomb;
    }

}
