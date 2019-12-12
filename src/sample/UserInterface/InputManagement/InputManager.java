package sample.UserInterface.InputManagement;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.UserInterface.Screen.Main;

/**
 * Created by Okur on 12/03/18.
 */
public class InputManager implements Runnable{

    private static Stage relatedscene = Main.getPrimaryStage();
    static private KeyCode pressedKey = null;
    static private boolean isKeyReleased = true;
    static private boolean running = true;

    static public KeyCode keyPressed() {
        return pressedKey;
    }


    static public boolean isKeyReleased() {
        return isKeyReleased;
    }

    public void run(){
        while (running){
            pressedKey = getPressedKey();
            //if(pressedKey != null)
                //System.out.println(pressedKey);
        }

    }

    static public KeyCode getPressedKey(){
        relatedscene.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                pressedKey = event.getCode();
            }
        });
        relatedscene.getScene().addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                pressedKey = null;
            }
        });
        return pressedKey;
    }

    public static void main (String args[]){
        new Thread(new InputManager()).start();

    }
}
