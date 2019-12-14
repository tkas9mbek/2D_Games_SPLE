package sample.ApplicationLogic.GameManagement;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import sample.ApplicationLogic.GameEntities.*;
import sample.UserInterface.InputManagement.InputManager;
import sample.UserInterface.Screen.GameOverPane;
import sample.UserInterface.Screen.Main;

import java.util.ArrayList;

public class GameEngine implements Runnable{

    private boolean isGamePaused;
    private Map gameMap;
    private CollisionManager cm;
    private static GameEngine gameEngine;

    public Map getGameMap() {
        return gameMap;
    }

    public void setGameMap(Map gameMap) {
        this.gameMap = gameMap;
    }

    public void setGamePaused(boolean gamePaused) {
        isGamePaused = gamePaused;
    }

    private Thread t;

    public void setT(Thread t) {
        this.t = t;
    }

    private GameEngine(){
        isGamePaused = false;
        gameMap = Map.getMap();
        cm = CollisionManager.getInstance();
    }


    public void run(){
        try{
            while (true) {
                if(!isGamePaused){
                    gameMap.update();
                    checkCollision();
                    if(gameMap.getHero().getHealth().getHealthAmount() <= 0){

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
            t = new Thread(this);
            t.start();
        }
    }

    public synchronized void update(){
        try{
            KeyCode checkKey = InputManager.getPressedKey();
            if(checkKey != null){
                if(checkKey.toString().equals("ESCAPE")){
                    Platform.runLater(
                            () -> {
                                Parent root = Map.getMap().pauseGame(true);
                                Main.getPrimaryStage().getScene().setRoot(root);
                            }
                    );

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setGameEngine(GameEngine gameEngine) {
        GameEngine.gameEngine = gameEngine;
    }

    public static GameEngine getGameEngine(){
        if(gameEngine == null){
            gameEngine = new GameEngine();
        }
        return gameEngine;
    }

    public void checkCollision(){
        boolean flag;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3;
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        if(Map.getMap().getTotalObjects() > 0){
            enemies = Map.getMap().getVisibleEnemies();
            powerUps = Map.getMap().getVisiblePowerUps();
        }

        for (Enemy enemy : enemies) {
            try {
                flag = cm.checkGameObjectCollision(enemy, Map.getMap().getHero());
                if (flag) {
                    Map.getMap().getHero().healthDecrease(1);
                    enemy.decreaseHealth(1);
                }
                for (int j = 0; j < Map.getMap().getHero().getBullets().size() && !flag1; j++) {
                    flag1 = cm.checkGameObjectCollision(enemy, Map.getMap().getHero().getBullets().get(j));
                    if (flag1) {
                        enemy.decreaseHealth(Map.getMap().getHero().getBullets().get(j).getDamage());
                        Map.getMap().getHero().getBullets().remove(j);
                        if (enemy.getHealth() <= 0) {
                            Map.getMap().getHero().updateExperience(enemy.getExperiencePrize());
                            Map.getMap().setScore(enemy.getScorePrize());
                        }
                    }
                }
                if (enemy.toString().equals("Big Enemy") || enemy.toString().equals("Boss")) {
                    ArrayList<Bullet> bullets;
                    if (enemy.toString().equals("Big Enemy"))
                        bullets = ((BigEnemy) enemy).getBullets();
                    else
                        bullets = ((Boss) enemy).getBullets();
                    for (int j = 0; j < bullets.size() && !flag2; j++) {
                        flag2 = cm.checkGameObjectCollision(Map.getMap().getHero(), bullets.get(j));
                        if (flag2) {
                            if (enemy.toString().equals("Big Enemy")) {
                                Map.getMap().getHero().healthDecrease(((BigEnemy) enemy).getBullets().get(j).getDamage());
                                ((BigEnemy) enemy).getBullets().remove(j);
                            } else {
                                System.out.println("boss damage");
                                Map.getMap().getHero().healthDecrease(((Boss) enemy).getBullets().get(j).getDamage());
                                ((Boss) enemy).getBullets().remove(j);
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (PowerUp powerUp : powerUps) {
            try {
                flag3 = cm.checkGameObjectCollision(powerUp, Map.getMap().getHero());
                if (flag3) {
                    switch (powerUp.getID()) {
                        case (1): {
                            Map.getMap().getHero().regenHealth(powerUp);
                            powerUp.setUsed(true);
                            break;
                        }
                        case (2): {
                            Map.getMap().getHero().regenEnergy(powerUp);
                            powerUp.setUsed(true);
                            break;
                        }
                    }
                    Map.getMap().setScore(powerUp.getScore());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}