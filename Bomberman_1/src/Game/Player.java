/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author jeffrey
 */
public class Player {

   private String name;
    private int Speed;
    private Boolean kick;
    private int Bomb_Count;
    
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int Speed) {
        this.Speed = Speed;
    }

    public Boolean getKick() {
        return kick;
    }

    public void setKick(Boolean kick) {
        this.kick = kick;
    }

    public int getBomb_Count() {
        return Bomb_Count;
    }

    public void setBomb_Count(int Bomb_Count) {
        this.Bomb_Count = Bomb_Count;
    }
    

    
    
    
}
