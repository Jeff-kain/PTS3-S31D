/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;

/**
 *
 * @author jeffrey
 */
public class Game {


    private static Game instance;
    private String naam;
    private String Beschrijving;
    private static Playground playground;
    private Team team1;
    private Team team2;

    private Game() {
        playground = new Playground();
    }

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

    public Playground playground() {
        return playground;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(team1.getPlayers());
        players.addAll(team2.getPlayers());

        return players;
    }

    public ArrayList<Player> getPlayer1() {
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(team1.getPlayers());
        return players;
    }

    public ArrayList<Player> getPlayer2() {
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(team1.getPlayers());
        return players;
    }

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
            return instance;
        }

        return instance;
    }
}
