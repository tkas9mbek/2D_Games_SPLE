package sample.ApplicationLogic.GameManagement;

import sample.ApplicationLogic.GameEntities.*;

import java.io.FileNotFoundException;

abstract public class AbstractEngine implements Runnable{
    public boolean isGamePaused;
    public CollisionManager cm;
    public Thread t;
    public MapFactory mapFactory = new MapFactory();

    public void setT(Thread t) {
        this.t = t;
    }

    abstract public void run();

    public void gameLoop(){
        if(t == null){
            t = new Thread(this);
            t.start();
        }
    }

    abstract public void checkCollision() throws FileNotFoundException;
}
