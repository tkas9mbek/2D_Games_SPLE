package games.ApplicationLogic.GameEntities;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import java.util.ArrayList;

abstract public class AbstractMap implements Runnable{

    public Parent root;
    public Parent pauseRoot;
    public ArrayList<GameObject> gameObjects;
    public AnimationTimer at;
    public HeroFactory heroFactory = new HeroFactory();

    public void clearGameObjects(){
        gameObjects.clear();
    }
    abstract public void setEnemies();
    abstract public void update();
    abstract public void createContent();

    public void setRoot(Parent root) {
        this.root = root;
    }

    public GameObject getGameObject(double x, double y){
        GameObject returnval = null;
        for(int i = 0; i < gameObjects.size(); i++){
            if(gameObjects.get(i).getXPos() == x && gameObjects.get(i).getYPos() == y){
                returnval = gameObjects.get(i);
            }
        }
        return returnval;
    }

    public Parent pauseGame(boolean gamePaused){
        if(gamePaused){
            at.stop();
        }
        else{
            at.start();
        }
        return pauseRoot;
    }
}