package Kappa; /**
 * Created by Ferhat on 8-12-2015.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ferhat on 8-12-2015.
 */
public class Positie implements Serializable {

    public CopyOnWriteArrayList<Positie> objecten = new CopyOnWriteArrayList<>();
    int x;
    int y;
    int z;

    public Positie(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        objecten.add(this);
    }

    @Override
    public String toString() {
        return "Positie " + "X:" + x + " Y:" + y + " Z:" + z;
    }
}
