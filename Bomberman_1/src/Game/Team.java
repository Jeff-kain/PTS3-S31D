/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.lang.Object;
import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author jeffrey
 */
public class Team implements IGameObject {

    private TeamColor color;
    private float maxHealth = 100f;
    private float currentHealth = 80f;
    private final ArrayList<Player> players;
    private float x;
    private float y;
    private Image sprite;

    public ArrayList<Player> getPlayers() {
        System.out.println("Size: " + players.size());
        return players;
    }

    public Team(SpriteSheet sprites, TeamColor color, float x, float y) {
        this.color = color;
        players = new ArrayList<>();
        this.x = x;
        this.y = y;
        if (color == TeamColor.BLUE) {
            sprite = sprites.getSubImage(0, 0);
        } else {
            sprite = sprites.getSubImage(1,0 );
        }

    }

    public void addPlayer(Player player) {
        players.add(player);
        System.out.println(players.size());
    }

    public TeamColor getColor() {
        return color;
    }

    public float maxHealth() {
        return maxHealth;
    }

    public float currentHealth() {
        return currentHealth;
    }

    public void damage(float damage) {
        if (currentHealth() > 0) {
            currentHealth = currentHealth - damage;
        }
    }

    public Player getPlayer(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public Image getSprite() {
        return sprite;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public void Update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
