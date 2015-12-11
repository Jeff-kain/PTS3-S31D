package portal.Models;

/**
 * Created by tverv on 08-Dec-15.
 */
public class Game {
    private String name;
    private String description;
    private String path;

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
