/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import portal.Models.Game;
import portal.Models.Score;
import portalserver.User;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Rob
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance = null;
    private Properties props;
    private Connection conn;
    
    private String url;
    private String username;
    private String password;
    
    private DatabaseConnection() throws IOException {
        props = new Properties();
	    InputStream input = null;
        File file = new File("./src/database/config.properties");
        
        try {
            input = new FileInputStream(file);

            // load a properties file
            props.load(input);

            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DatabaseConnection getInstance() throws IOException {
        if(instance != null) {
            return instance;
        } else {
            instance = new DatabaseConnection();
            return instance;
        }
    }

    public boolean open() {
        try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(url, username, password);
        return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean close() {
        try {
            conn.close();
            conn = null;
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean TestConnection(){
        boolean isOpen = open();
        close();
        return isOpen;
    }
    
    public boolean CheckLogin(String username, String password) {

        try {
            boolean isOpen = open();
            if (isOpen) {

                ResultSet rs;
                Statement stat = conn.createStatement();

                String query = "SELECT * FROM USERS WHERE NAME ='" + username + "' AND PASSWORD = '" + password + "';";

                stat = conn.prepareStatement(query);
                rs = stat.executeQuery(query);

                return rs.first();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally{            
            close();
        }
        return false;
    }

    public boolean CheckUsername(String username) {

        try {
            boolean isOpen = open();

            if(isOpen) {
            ResultSet rs;
            Statement stat = conn.createStatement();

            String query = "SELECT * FROM USERS WHERE NAME ='" + username + "';";

            stat = conn.prepareStatement(query);
            rs = stat.executeQuery(query);

            return rs.first();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally{
            close();
        }
        return false;
    }
    
    public boolean CreateUser(String username, String password) {

        try {
            boolean isOpen = open();
            if (isOpen) {
                Statement stat = conn.createStatement();
                String query = "INSERT users (Name, Password) VALUES ('" + username + "', '" + password + "');";
                stat = conn.prepareStatement(query);

                return true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally{            
            close();
        }
        return false;
    }

    public User getUser(String username, String password) {

        try {
            boolean isOpen = open();
            if (isOpen) {
                System.out.println(username);
                System.out.println(password);

                ResultSet rs;
                Statement stat;
                String query = "SELECT * FROM USERS WHERE NAME ='" + username + "' AND PASSWORD = '" + password + "';";

                stat = conn.prepareStatement(query);
                rs = stat.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("NAME");
                    System.out.println("User" + id + " - " + name);

                    return new User(id, name);
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally{
            close();
        }
        return null;
    }
    
    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();

        try {
            boolean isOpen = open();
            if (isOpen) {
                ResultSet rs;
                Statement stat = null;

                String query = "select * from GAMES";

                stat = conn.prepareStatement(query);
                rs = stat.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("NAME");
                    String description = rs.getString("DESCRIPTION");

                    Game game = new Game(id, name, description);

                    games.add(game);
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally{
            close();
        }

        return games;
    }
    
    public List<Score> getLeaderboard(String game) {

        List<Score> leaderboard = new ArrayList<>();
        ResultSet rs;
        Statement stat;

        try {
            boolean isOpen = open();
            if (isOpen) {

                String query = "SELECT lb.Wins, lb.losses, u.Name FROM users u, leaderboard lb, games g WHERE u.id = lb.id_user AND g.id = lb.id_Game  AND g.Name = '" + game + "'";

                stat = conn.prepareStatement(query);
                rs = stat.executeQuery(query);

                while (rs.next()) {
                    int wins = rs.getInt("Wins");
                    int losses = rs.getInt("Losses");
                    String name = rs.getString("Name");

                    double totalGames = (double) wins + losses;
                    double winratio = (wins / totalGames) * 100;
                    winratio = Math.round(winratio);

                    leaderboard.add(new Score(winratio, wins, losses, name));
                }
                return leaderboard;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally{
            close();
        }
        return null;
    }
    
    public Score getScoresPlayer(String enteredName, String game) {

        Score s = null;
        ResultSet rs;
        Statement stat;

        try {
            boolean isOpen = open();
            if (isOpen) {

                String query = "SELECT lb.Wins, lb.losses FROM users u, leaderboard lb, games g WHERE u.id = lb.id_user AND g.id = lb.id_Game AND u.Name = '" + enteredName + "'  AND g.Name = '" + game + "'";
                ;

                stat = conn.prepareStatement(query);
                rs = stat.executeQuery(query);

                while (rs.next()) {
                    int wins = rs.getInt("Wins");
                    int losses = rs.getInt("Losses");
                    String name = enteredName;

                    double totalGames = (double) wins + losses;
                    double winratio = (wins / totalGames) * 100;
                    winratio = Math.round(winratio);

                    s = new Score(winratio, wins, losses, name);
                }
                return s;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally{
            close();
        }
        return null;
    }
}
