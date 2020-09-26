package games.ApplicationLogic.GameEntities;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EscapeMap extends AbstractMap {
    private double score;
    private Text head;
    private final String DIR_LOC = "file:/" + System.getProperty("user.dir").replace("\\", "/") +
            "/game-assets/";
    private final String SECOND_LEVEL_BACKGROUND_IMAGE = "ApplicationLogic/GameEntities/images/prison2.jpg";

    private Thread t;
    private BackgroundImage backgroundImage;
    private ObjectRandomLocationManager locationManager;
    private Skeleton hero;

    public double getScore() {
        return score;
    }

    public void run(){
    }

    public EscapeMap() throws FileNotFoundException {
        super();
        root = new GridPane();
        gameObjects = new ArrayList<>();
        locationManager = new ObjectRandomLocationManager();
        setBackgroundImage(SECOND_LEVEL_BACKGROUND_IMAGE);
        setEnemies();
        try{
            hero = heroFactory.getSkeleton();
        }catch (Exception e){
            e.printStackTrace();
        }
        score = 0;
    }

    public void setBackgroundImage(String backgroundImage){
        this.backgroundImage = new BackgroundImage(new javafx.scene.image.Image(DIR_LOC + backgroundImage,852,480,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        ((GridPane)this.root).setBackground(new Background(this.backgroundImage));
        ((GridPane)this.root).setPrefSize(852,480);
    }

    public void setEnemies(){
        try{
            gameObjects.clear();
            GameObject gameObject;
            double yLoc = 25;
            double x;

            for (int i = 5; i < 400; i = i + 120) {
                gameObject = new SmallEnemy(5, i, true);
                gameObjects.add(gameObject);
            }

            for(int i = 800; i < 50000;) {

                int choice = (int) (Math.random() * 19);
                if (choice < 7) {
                    locationManager.generateLocation(i = i + 100, i + 25, 30, 400);
                    x = locationManager.getX();
                    yLoc = (yLoc + (int) (Math.random() * 120 + 120)) % 440;
                    gameObject = new Obstacle(x, yLoc, true, 2);
                    gameObjects.add(gameObject);

                } else if (choice < 8) {
                    locationManager.generateLocation(i = i + 150, i + 25, 30, 400);
                    x = locationManager.getX();
                    yLoc = yLoc > 200 ? 3 : 337;
                    gameObject = new Obstacle(x, yLoc, true, 1);
                    gameObjects.add(gameObject);
                    yLoc = (yLoc + (int) (Math.random() * 140 + 140)) % 440;

                } else if (choice < 14) {
                    locationManager.generateLocation(i = i + 150, i + 25, 30, 400);
                    x = locationManager.getX();
                    yLoc = (yLoc + (int) (Math.random() * 85 + 90)) % 420;
                    gameObject = new Trap(x, yLoc, true);
                    gameObjects.add(gameObject);

                } else if (choice < 16) {
                    locationManager.generateLocation(i = i + 50, i + 250, 30, 400);
                    gameObject = new PowerUp(locationManager.getX(), locationManager.getY(), (int) (Math.random() * 2 + 1), 0);
                    gameObject.setVisible(false);
                    gameObjects.add(gameObject);

                } else {
                    locationManager.generateLocation(i = i + 50, i + 250, 30, 400);
                    gameObject = new PowerUp(locationManager.getX(), locationManager.getY(), 3, 0);
                    gameObject.setVisible(false);
                    gameObjects.add(gameObject);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void update() {
        score = score + 0.0015;
        for (int i = 0; i < gameObjects.size(); i++) {
            try {
                if (gameObjects.get(i).getXPos() <= 0) {
                    gameObjects.remove(i);
                }
            } catch (Exception ignored) {

            }
        }
    }

    public void createContent(){
        try{
            Canvas canvas = new Canvas(850, 480);
            ((GridPane)root).getChildren().add( canvas );

            GraphicsContext gc = canvas.getGraphicsContext2D();
            at = new AnimationTimer()
            {
                double lastNanoTime = System.nanoTime();
                public void handle(long currentNanoTime)
                {
                    try{
                        head.setText("Score: " + (int)score);

                        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                        lastNanoTime = currentNanoTime;

                        gc.clearRect(0, 0, 850,480);
                        hero.update(elapsedTime);
                        hero.draw(gc);
                        hero.controlHero();

                        for (GameObject gameObject : gameObjects) {
                            gameObject.update(elapsedTime);
                            gameObject.draw(gc);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };
            at.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Parent load(){
        root.getStylesheets().add("games/UserInterface/Screen/style.css");
        createContent();
        head = new Text("Score: " + (int)score);
        head.setTranslateX(350);
        head.setTranslateY(-200);
        head.setId("score-text");
        if(t == null){
            t = new Thread(this);
            t.start();
        }
        ((GridPane)root).getChildren().add(head);
        return root;
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

    public ArrayList<Obstacle> getVisibleObstacles(){
        ArrayList <Obstacle> obstacles = new ArrayList<>();
        int count = gameObjects.size();
        for(int i = 0; i < count; i++){
            if(gameObjects.get(i) != null)
                if(gameObjects.get(i).isVisible())
                    if(gameObjects.get(i).toString().equals("Obstacle"))
                        obstacles.add((Obstacle)gameObjects.get(i));
        }
        return obstacles;
    }
    public ArrayList<SmallEnemy> getVisibleGuardians(){
        ArrayList <SmallEnemy> guardians = new ArrayList<>();
        int count = gameObjects.size();
        for(int i = 0; i < count; i++){
            if(gameObjects.get(i) != null)
                if(gameObjects.get(i).isVisible())
                    if(gameObjects.get(i).toString().equals("Guardian"))
                        guardians.add((SmallEnemy)gameObjects.get(i));
        }
        return guardians;
    }
    public ArrayList<Trap> getVisibleTraps(){
        ArrayList <Trap> traps = new ArrayList<>();
        int count = gameObjects.size();
        for(int i = 0; i < count; i++){
            if(gameObjects.get(i) != null)
                if(gameObjects.get(i).isVisible())
                    if(gameObjects.get(i).toString().equals("Trap"))
                        traps.add((Trap)gameObjects.get(i));
        }
        return traps;
    }

    public ArrayList<PowerUp> getVisiblePowerUps(){
        ArrayList <PowerUp> powerUps = new ArrayList<>();
        int count = gameObjects.size();
        for(int i = 0; i < count; i++){
            if(gameObjects.get(i) != null)
                if(gameObjects.get(i).isVisible())
                    if(gameObjects.get(i).toString().equals("Power Up"))
                        powerUps.add((PowerUp) gameObjects.get(i));
        }
        return powerUps;
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