/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.Models;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Rob
 */
public final class Score implements Serializable, Comparable<Score>{
    
    private int wins;
    private int losses;
    private String user;
    private double winratio;
    
    public Score(double winratio, int wins, int losses, String user){
        this.wins = wins;
        this.losses = losses;
        this.user = user;
        this.winratio = winratio;
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

    public double getWinratio() {
        return winratio;
    }

    public void setWinratio(double winratio) {
        this.winratio = winratio;
    }

    @Override
    public int compareTo(Score t) {
        return this.user.compareToIgnoreCase(t.getUser());
    }

    public static Comparator<Score> ScoreComparator = new Comparator<Score>() {
                
        @Override
        public int compare(Score t1, Score t2) {
            if (t1.getWinratio() < t2.getWinratio() && t1.getWins() < t2.getWins()) {
               return -1;
            } else if (t1.getWinratio() > t2.getWinratio() && t1.getWins() > t2.getWins()) {
               return 1;
            } else {
               return 0;
            }
        }
    };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append(user + " - ");
        sb.append("winratio: " + winratio + "% - ");
        sb.append("wins: " + wins + " - ");
        sb.append("total games: " + (wins + losses));
        return sb.toString();
    }
}
