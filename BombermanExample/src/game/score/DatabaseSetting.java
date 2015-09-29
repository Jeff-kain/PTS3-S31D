package game.score;

/**
 *
 * @author Michal
 */
public class DatabaseSetting {
    /**
     *
     */
    public static final String DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    /**
     *
     */
    public static final String URL = "jdbc:derby://localhost/Bomberman";
    /**
     *
     */
    public static final String USER = "michal";
    /**
     *
     */
    public static final String PASSWORD = "michal";

    /**
     *
     */
    public static final String QUERY_CREATE_BEST_TIMES = "CREATE TABLE best_score (name VARCHAR(128) NOT NULL, time INT NOT NULL, enemies INT NOT NULL, walls INT NOT NULL, items INT NOT NULL)";
    /**
     *
     */
    public static final String QUERY_ADD_BEST_TIME = "INSERT INTO best_score (name, time, enemies, walls, items) VALUES (?, ?, ?, ?, ?)";
    /**
     *
     */
    public static final String QUERY_SELECT_BEST_TIMES = "SELECT name, time, enemies, walls, items FROM best_score";

    private DatabaseSetting() {}
}
