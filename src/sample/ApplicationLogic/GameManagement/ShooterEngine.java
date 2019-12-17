package sample.ApplicationLogic.GameManagement;

import javafx.application.Platform;
import sample.ApplicationLogic.GameEntities.*;
import sample.UserInterface.Screen.GameOverPane;
import sample.UserInterface.Screen.Main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ShooterEngine extends AbstractEngine{

    private ShooterMap gameShooterMap;

    public ShooterEngine(){
        isGamePaused = false;
        gameShooterMap = mapFactory.getShooterMap();
        cm = CollisionManager.getInstance();
    }

    public void run(){
        try{
            while (true) {
                if(!isGamePaused){
                    gameShooterMap.update();
                    checkCollision();
                    if(gameShooterMap.heroFactory.getMage().getHealth().getHealthAmount() <= 0){
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

    public void gameLoop(){
        if(t == null){
            t = new Thread(this);
            t.start();
        }
    }

    @Override
    public void checkCollision() throws FileNotFoundException {
        boolean flag;
        ShooterMap map = mapFactory.getShooterMap();
        ArrayList<Enemy> enemies;
        ArrayList<PowerUp> powerUps;
        enemies = map.getVisibleEnemies();
        powerUps = map.getVisiblePowerUps();
        Mage hero = map.heroFactory.getMage();

        for (Enemy enemy : enemies) {
            try {
                flag = cm.checkGameObjectCollision(enemy,hero);
                if (flag) {
                    hero.healthDecrease(1);
                    enemy.decreaseHealth(1);
                }
                for (int j = 0; j < hero
                        .getBullets().size() && !flag; j++) {
                    flag = cm.checkGameObjectCollision(enemy, hero.getBullets().get(j));
                    if (flag) {
                        enemy.decreaseHealth(hero.getBullets().get(j).getDamage());
                        hero.getBullets().remove(j);
                        if (enemy.getHealth() <= 0) {
                            hero.updateExperience(enemy.getExperiencePrize());
                            map.setScore(enemy.getScorePrize());
                        }
                    }
                }
                if (enemy.toString().equals("Big Enemy") || enemy.toString().equals("Boss")) {
                    ArrayList<Bullet> bullets;
                    if (enemy.toString().equals("Big Enemy"))
                        bullets = ((BigEnemy) enemy).getBullets();
                    else
                        bullets = ((Boss) enemy).getBullets();
                    for (int j = 0; j < bullets.size() && !flag; j++) {
                        flag = cm.checkGameObjectCollision(hero
                                , bullets.get(j));
                        if (flag) {
                            if (enemy.toString().equals("Big Enemy")) {
                                hero
                                        .healthDecrease(((BigEnemy) enemy).getBullets().get(j).getDamage());
                                ((BigEnemy) enemy).getBullets().remove(j);
                            } else {
                                hero
                                        .healthDecrease(((Boss) enemy).getBullets().get(j).getDamage());
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
                flag = cm.checkGameObjectCollision(powerUp, hero
                );
                if (flag) {
                    switch (powerUp.getID()) {
                        case (1): {
                            hero.regenHealth(powerUp);
                            powerUp.setUsed(true);
                            break;
                        }
                        case (2): {
                            hero.regenEnergy(powerUp);
                            powerUp.setUsed(true);
                            break;
                        }
                    }
                    map.setScore(powerUp.getScore());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}