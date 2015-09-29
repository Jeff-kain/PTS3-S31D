/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.score;

/**
 *
 * @author Michal
 */
public class Score implements Comparable<Score> {

    private static Score instance = new Score();
    private int enemiesKilled;
    private int wallsDestroyed;
    private int itemsUsed;
    private int playingTime;
    private long startTime;
    private String name;

    private Score() {
        enemiesKilled = 0;
        wallsDestroyed = 0;
        itemsUsed = 0;
        startTime = System.currentTimeMillis();
        name = System.getProperty("user.name");
    }
    
    /**
     * gets new instance of score
     */
    public static void reloadScore(){
        instance = new Score();
    }

    /**
     * loads to instance variables parameters with values
     * @param name name of user
     * @param time playing time
     * @param enemies number of killed enemies
     * @param walls number of destroyed walls
     * @param items number of used items
     */
    public void loadData(String name, int time, int enemies, int walls, int items) {
        this.name = name;
        this.playingTime = time;
        this.enemiesKilled = enemies;
        this.wallsDestroyed = walls;
        this.itemsUsed = items;
    }

    /**
     * sets all variables with statistics to 0 and startTime to current time
     */
    public void restart() {
        enemiesKilled = 0;
        wallsDestroyed = 0;
        itemsUsed = 0;
        startTime = System.currentTimeMillis();
    }

    /**
     * gets instance of score
     * @return instance of score
     */
    public static Score getScore() {
        return instance;
    }

    /**
     * increments number of destroyed walls
     */
    public void incWallsDestroyed() {
        wallsDestroyed++;
    }

    /**
     * increments number of killed enemies
     */
    public void incEnemiesKilled() {
        enemiesKilled++;
    }

    /**
     * increments number of used items
     */
    public void incItemsUsed() {
        itemsUsed++;
    }

    /**
     * calculates and gets actual playing time
     * @return the playing time
     */
    public int getPlayingTime() {
        return (int) (System.currentTimeMillis() - startTime) / 1000;
    }
    
    /**
     * gets playing time
     * @return playing time
     */
    public int PlayingTime() {
        return playingTime;
    }

    /**
     * @return the number of killed enemies
     */
    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    /**
     * @return the number of destroyed walls
     */
    public int getWallsDestroyed() {
        return wallsDestroyed;
    }

    /**
     * @return the number of used items
     */
    public int getItemsUsed() {
        return itemsUsed;
    }

    /**
     * gets name of user playing game
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * compares score of 2 players according to player's times and number of killed enemies
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Score o) {
        if (o.PlayingTime() > this.PlayingTime()) {
            return -1;
        } else if (o.PlayingTime() < this.PlayingTime()) {
            return 1;
        } else {
            if (o.getEnemiesKilled() > this.getEnemiesKilled()) {
                return -1;
            } else if (o.getEnemiesKilled() < this.getEnemiesKilled()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}