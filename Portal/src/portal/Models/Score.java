/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.Models;

/**
 *
 * @author Rob
 */
public final class Score {
    
    private int wins;
    private int losses;
    private String user;
    
    public Score(int wins, int losses, String user){
        this.wins = wins;
        this.losses = losses;
        this.user = user;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append(user + " - ");
        int percentage = wins / (wins + losses) * 100;
        sb.append("winratio: " + percentage + "% - ");
        sb.append("wins: " + wins + " - ");
        sb.append("total games: " + wins + losses);
        return sb.toString();
    }
}
