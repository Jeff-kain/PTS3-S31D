/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.score;

/**
 *
 * @author Michal
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michal
 */
public class BestScore implements Iterable<Score> {

    /**
     * List of best player scores.
     */
    private List<Score> bestScore = new ArrayList<Score>();

    /**
     * Returns an iterator over a set of best times.
     *
     * @return an iterator
     */
    public Iterator<Score> iterator() {
        return bestScore.iterator();
    }

    /**
     * Adds player time and other statistics to best score.
     * @param score 
     */
    public void addScore(Score score) {
        bestScore.add(score);
        insertToDB(score);
        Collections.sort(bestScore);
    }

    private void insertToDB(Score score){
        try {
            try {
                Class.forName(DatabaseSetting.DRIVER_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BestScore.class.getName()).log(Level.SEVERE, null, ex);
            }
                Connection connection = DriverManager.getConnection(DatabaseSetting.URL,
                                            DatabaseSetting.USER, DatabaseSetting.PASSWORD);
        Statement stm = connection.createStatement();
    try {
        stm.executeUpdate(DatabaseSetting.QUERY_CREATE_BEST_TIMES);
    }catch (Exception e) {
        //do not propagate exception, table may already exist
    }
    stm.close();
    PreparedStatement pstm = connection.prepareStatement(DatabaseSetting.QUERY_ADD_BEST_TIME);
    pstm.setString(1, score.getName());
    pstm.setInt(2, score.getPlayingTime());
    pstm.setInt(3, score.getEnemiesKilled());
    pstm.setInt(4, score.getWallsDestroyed());
    pstm.setInt(5, score.getItemsUsed());
    pstm.execute();
    pstm.close();
    connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getSQLState());
            System.out.println( "Exception occured during saving high score to database: " + ex.getMessage());
        }
    }
    
    private void selectFromDB(){
        try {
            try {
                Class.forName(DatabaseSetting.DRIVER_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BestScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        Connection connection = DriverManager.getConnection(DatabaseSetting.URL, 
                                    DatabaseSetting.USER, DatabaseSetting.PASSWORD);
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(DatabaseSetting.QUERY_SELECT_BEST_TIMES);  
        bestScore.clear();
        while(rs.next()){
            Score score= Score.getScore();
            score.loadData(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
            bestScore.add(score);
            Score.reloadScore();
        }
        stm.close();
        connection.close();
        Collections.sort(bestScore);
        } catch (SQLException ex) {
            System.out.println(ex.getSQLState());
            System.out.println( "Exception occured during saving high score to database: " + ex.getMessage());
        }
    }
    
    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        Formatter f = new Formatter();
        int i = 1;
        selectFromDB();
        for (Score sc : bestScore) {
            f.format("%d. %s      %3d secs          %2d            %2d            %2d\n", i, sc.getName(), sc.PlayingTime(),sc.getEnemiesKilled(), sc.getWallsDestroyed(), sc.getItemsUsed());
            i++;
        }
        return f.toString();
    }
}
