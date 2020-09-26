package games.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.IOException;

public class Item extends GameObject {

    private final String PROJECT_DIR = System.getProperty("user.dir") + "\\game-assets\\";
    private final String GOLD = PROJECT_DIR + "\\ApplicationLogic\\GameEntities\\images\\gold.png";

    private String name;

    // ID constructor
    public Item(double xPos, double yPos, int ID, String name) throws IOException {
        super(xPos, yPos);
        setName(name);
        setSpriteImage(new Image(new FileInputStream(GOLD)));
        setType("Item");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
