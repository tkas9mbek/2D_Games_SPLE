package sample.ApplicationLogic.GameManagement;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import sample.ApplicationLogic.GameEntities.*;
import sample.UserInterface.InputManagement.InputManager;
import sample.UserInterface.Screen.GameOverPane;
import sample.UserInterface.Screen.Main;

import java.util.ArrayList;

public class EscapeEngine implements Runnable{
    private boolean isGamePaused;
    private EscapeMap gameEscapeMap;
    private CollisionManager cm;
    private static EscapeEngine gameEngine;

    public EscapeMap getGameEscapeMap() {
        return gameEscapeMap;
    }

    public void setGameEscapeMap(EscapeMap gameEscapeMap) {
        this.gameEscapeMap = gameEscapeMap;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        isGamePaused = gamePaused;
    }

    private Thread t;

    public void setT(Thread t) {
        this.t = t;
    }

    private EscapeEngine(){
        isGamePaused = false;
        gameEscapeMap = EscapeMap.getEscapeMap();
        cm = CollisionManager.getInstance();
    }

    public void run(){
        try{
            while (true) {
                if(!isGamePaused){
                    //System.out.println("entered");

                    //System.out.println(isGamePaused);

                    gameEscapeMap.update();
                    checkCollision();
                    if(gameEscapeMap.getHero().isDead()){
                        Platform.runLater(
                                () -> {
                                    Main.getPrimaryStage().setScene(GameOverPane.getInstance().getScene());
                                }
                        );
                    }

                }
                update();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void gameLoop(){
        if(t == null){
            //System.out.println(isGamePaused);
            t = new Thread(this);
            t.start();
        }
    }
    public synchronized void update(){
        try{
            KeyCode checkKey = InputManager.getPressedKey();
            if(checkKey != null){
                //System.out.println(InputManager.getPressedKey());
                if(checkKey.toString().equals("ESCAPE")){
                    isGamePaused = true;
                    if(isGamePaused){
                        Platform.runLater(
                                () -> {
                                    Parent root = EscapeMap.getEscapeMap().pauseGame(true);
                                    Main.getPrimaryStage().getScene().setRoot(root);
                                }
                        );

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setGameEngine(EscapeEngine gameEngine) {
        EscapeEngine.gameEngine = gameEngine;
    }

    public static EscapeEngine getGameEngine(){
        if(gameEngine == null){
            gameEngine = new EscapeEngine();
        }
        return gameEngine;
    }

    public void checkCollision(){
        boolean flag;

        ArrayList<Obstacle> obstacles;
        ArrayList<PrisonGuardian> guardians;
        ArrayList<Trap> traps;
        ArrayList<EscapePowerUp> powerUps;

        obstacles = EscapeMap.getEscapeMap().getVisibleObstacles();
        guardians = EscapeMap.getEscapeMap().getVisibleGuardians();
        traps = EscapeMap.getEscapeMap().getVisibleTraps();
        powerUps = EscapeMap.getEscapeMap().getVisiblePowerUps();

        Skeleton hero = EscapeMap.getEscapeMap().getHero();

        for (Obstacle obstacle : obstacles) {
            try {
                flag = cm.checkGameObjectCollision(obstacle, EscapeMap.getEscapeMap().getHero());
                if (flag) {
                    if(hero.getXPos() <= obstacle.getXPos()) {
                        hero.setLocation(hero.getXPos() - 4, hero.getYPos());
                    } else {
                        hero.setLocation(hero.getXPos() + 4, hero.getYPos());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (PrisonGuardian guardian : guardians) {
            try {
                flag = cm.checkGameObjectCollision(guardian, EscapeMap.getEscapeMap().getHero());
                if (flag) {
                    hero.setDead(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Trap trap : traps) {
            try {
                flag = cm.checkGameObjectCollision(trap, EscapeMap.getEscapeMap().getHero());
                if (flag) {
                    if(hero.isShielded()){
                        hero.setShielded(false);
                        trap.setLocation(0, 0);
                    } else {
                        hero.setDead(true);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (EscapePowerUp powerUp : powerUps) {
            try {
                flag = cm.checkGameObjectCollision(powerUp, EscapeMap.getEscapeMap().getHero());
                if (flag) {
                    powerUp.setLocation(0, 0);
                    switch (powerUp.getID()) {
                        case (1): {
                            hero.setVELOCITY(hero.getVELOCITY() + 50);
                            break;
                        }
                        case (2): {
                            hero.setShielded(true);
                            break;
                        }
                        case (3): {
                            hero.setVELOCITY( (int) (hero.getVELOCITY() / 1.6) );
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
