package portal.Models;

import java.io.Serializable;

/**
 * Created by tverv on 08-Dec-15.
 */
public class Game implements Serializable{
    private int id;
    private String name;
    private String description;

    public Game(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
