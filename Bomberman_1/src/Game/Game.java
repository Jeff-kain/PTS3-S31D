/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author jeffrey
 */
public class Game {

    private String naam;
    private String Beschrijving;
    private static Playground playground = new Playground();
    private static Team team1;
    private static Team team2;

    public void setTeam1(SpriteSheet sprites, TeamColor Color, float x, float y) {
        team1 = new Team(sprites, Color, x, y);
    }

    public void setTeam2(SpriteSheet sprites, TeamColor Color, float x, float y) {
        team2 = new Team(sprites, Color, x, y);
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public Game() {

    }

    public Playground playground() {
        return playground;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }
}
