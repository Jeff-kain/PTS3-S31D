/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerup;

/**
 *
 * @author jeffrey
 */
public class Kick extends PowerUp {
    
   public int x;
    public int y;
    public Boolean visible;
    public String name = "Kick_Up";
    
    public Kick(String name, int x, int y, Boolean visible)
    {
        super(name);
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Boolean isVisible()
    {
        return visible;
    }
    
    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
