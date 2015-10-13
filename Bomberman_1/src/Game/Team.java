/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.lang.Object;
import java.awt.Color ;
import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author jeffrey
 */
public class Team {
    private Color color;
    private float maxHealth =100f;
    private float currentHealth = 80f;
    private ArrayList<Player> players;

    public Color getColor() {
        return color;
    }
    
    public float maxHealth()
    {
        return maxHealth;
    }
   
    public float currentHealth()
    {
        return currentHealth;
    }
    
    public void damage()
    {
        if(currentHealth()>0)
        {
        currentHealth = currentHealth -0.01f;
        }
    }
    
    public Player getPlayer(String name)
    {
        for(Player p: players)
        {
            if(p.getName().equals(name))
            {
                return p;
            }
        }
        return null;
    }
    
}
