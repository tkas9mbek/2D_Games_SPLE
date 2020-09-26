package games.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;

public class Neutral extends GameObject {

    private final String PROJECT_DIR = System.getProperty("user.dir") + "\\game-assets\\";
    private final String SHAMAN = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\shaman.png";
    private final String HOME = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\home.png";

    private String hint;
    private int ID;

    // ID constructor
    public Neutral(double xPos, double yPos, int ID, String hint) throws IOException {
        super(xPos, yPos);
        setHint(hint);
        this.ID = ID;
        if( ID == 1) {
            setSpriteImage(new Image(new FileInputStream(SHAMAN)));
            setType("Neutral");
        } else {
            setSpriteImage(new Image(new FileInputStream(HOME)));
            setType("Home");
        }

    }

    public String getHint() {
        return hint;
    }

    public int getID(){
        return ID;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
