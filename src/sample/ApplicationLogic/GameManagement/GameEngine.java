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

    private GameEngine(){
        isGamePaused = false;
        gameMap = Map.getMap();
        cm = CollisionManager.getInstance();
    }


    public void run(){
        try{
            while (true) {
                if(!isGamePaused){
                    //System.out.println("entered");

                    //System.out.println(isGamePaused);

                    gameMap.update();
                    checkCollision();
                    if(gameMap.getHero().getHealth().getHealthAmount() <= 0){

                        Platform.runLater(
                                () -> {
                                    Main.getPrimaryStage().setScene(GameOverPane.getInstance().getScene());

                                    //x.closeWriter();
                                }
                        );
                    }

                }
                update();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        //while(true){


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
                                    Parent root = Map.getMap().pauseGame(true);
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

        //System.out.println(powerUps.size());
        //System.out.println(enemies.size());
        for(int i = 0; i < enemies.size(); i++){
            try{
                //System.out.println(enemies.get(i).getWidth());
                flag = cm.checkGameObjectCollision(enemies.get(i), Map.getMap().getHero());
                //System.out.println(flag);
                if(flag){
                    Map.getMap().getHero().healthDecrease(1);
                    enemies.get(i).decreaseHealth(1);
                }
                for(int j = 0; j < Map.getMap().getHero().getBullets().size() && !flag1; j++){
                    flag1 = cm.checkGameObjectCollision(enemies.get(i), Map.getMap().getHero().getBullets().get(j));
                    if(flag1){
                        //System.out.println("decrease enemy");
                        enemies.get(i).decreaseHealth(Map.getMap().getHero().getBullets().get(j).getDamage());
                        Map.getMap().getHero().getBullets().remove(j);
                        if(enemies.get(i).getHealth() <= 0){
                            Map.getMap().getHero().updateExperience(enemies.get(i).getExperiencePrize());
                            Map.getMap().setScore(enemies.get(i).getScorePrize());
                        }
                    }
                }
                if(enemies.get(i).toString().equals("Big Enemy") || enemies.get(i).toString().equals("Boss")){
                    ArrayList<Bullet> bullets;
                    if(enemies.get(i).toString().equals("Big Enemy"))
                        bullets = ((BigEnemy)enemies.get(i)).getBullets();
                    else
                        bullets = ((Boss)enemies.get(i)).getBullets();
                    for(int j = 0; j < bullets.size() && !flag2; j++){
                        flag2 = cm.checkGameObjectCollision(Map.getMap().getHero(), bullets.get(j));
                        if(flag2){
                            if(enemies.get(i).toString().equals("Big Enemy")){
                                Map.getMap().getHero().healthDecrease(((BigEnemy)enemies.get(i)).getBullets().get(j).getDamage());
                                ((BigEnemy)enemies.get(i)).getBullets().remove(j);
                            }
                            else{
                                System.out.println("boss damage");
                                Map.getMap().getHero().healthDecrease(((Boss)enemies.get(i)).getBullets().get(j).getDamage());
                                ((Boss)enemies.get(i)).getBullets().remove(j);
                            }

                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        for(int i = 0; i < powerUps.size(); i++){
            try{
                flag3 = cm.checkGameObjectCollision(powerUps.get(i), Map.getMap().getHero());
                //System.out.println(flag3);
                if(flag3){
                    switch (powerUps.get(i).getID()){
                        case(1): {Map.getMap().getHero().regenHealth(powerUps.get(i)); powerUps.get(i).setUsed(true); break;}
                        case(2): {Map.getMap().getHero().regenEnergy(powerUps.get(i)); powerUps.get(i).setUsed(true); break;}
                    }
                    Map.getMap().setScore(powerUps.get(i).getScore());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
