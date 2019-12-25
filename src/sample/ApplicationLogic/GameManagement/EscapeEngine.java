package sample.ApplicationLogic.GameManagement;

import javafx.application.Platform;
import sample.ApplicationLogic.GameEntities.*;
import sample.UserInterface.Screen.GameOverPane;
import sample.UserInterface.Screen.Main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EscapeEngine extends AbstractEngine{

    private EscapeMap gameEscapeMap;

    public EscapeEngine() throws FileNotFoundException {
        isGamePaused = false;
        gameEscapeMap = mapFactory.getEscapeMap();
        cm = CollisionManager.getInstance();
    }

    @Override
    public void run(){
        try{
            while (true) {
                if(!isGamePaused){

                    gameEscapeMap.update();
                    checkCollision();

                    if(gameEscapeMap.heroFactory.getSkeleton().isDead()){
                        Platform.runLater(
                                () -> Main.getPrimaryStage().setScene(GameOverPane.getInstance().getScene())
                        );
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void checkCollision() throws FileNotFoundException {
        boolean flag;

        ArrayList<Obstacle> obstacles;
        ArrayList<SmallEnemy> guardians;
        ArrayList<Trap> traps;
        ArrayList<PowerUp> powerUps;

        obstacles = mapFactory.getEscapeMap().getVisibleObstacles();
        guardians = mapFactory.getEscapeMap().getVisibleGuardians();
        traps = mapFactory.getEscapeMap().getVisibleTraps();
        powerUps = mapFactory.getEscapeMap().getVisiblePowerUps();

        Skeleton hero = mapFactory.getEscapeMap().heroFactory.getSkeleton();

        for (Obstacle obstacle : obstacles) {
            try {
                flag = cm.checkGameObjectCollision(obstacle, hero);
                if (flag) {
                    if(hero.getXPos() <= obstacle.getXPos()) {
                        hero.setLocation(hero.getXPos() - 2, hero.getYPos());
                    } else {
                        hero.setLocation(hero.getXPos() + 2, hero.getYPos());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (SmallEnemy guardian : guardians) {
            try {
                flag = cm.checkGameObjectCollision(guardian, hero);
                if (flag) {
                    hero.setDead(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Trap trap : traps) {
            try {
                flag = cm.checkGameObjectCollision(trap, hero);
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

        for (PowerUp powerUp : powerUps) {
            try {
                flag = cm.checkGameObjectCollision(powerUp, hero);
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
