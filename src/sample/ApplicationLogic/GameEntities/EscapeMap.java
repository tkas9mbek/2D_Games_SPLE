package sample.ApplicationLogic.GameEntities;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.UserInterface.Screen.PauseMenu;

import java.util.ArrayList;

public class EscapeMap implements Runnable{
    private Parent root;
    private double score;
    private Text head;
    private Parent pauseRoot;
    private final String SECOND_LEVEL_BACKGROUND_IMAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\prison2.jpg";
    private static EscapeMap map;
    private Thread t;
    private BackgroundImage backgroundImage;
    private ObjectRandomLocationManager locationManager;
    private ArrayList<GameObject> gameObjects;
    private Skeleton hero;
    private AnimationTimer at;

    public double getScore() {
        return score;
    }

    public void run(){
    }

    private EscapeMap(){
        root = new GridPane();
        gameObjects = new ArrayList<>();
        locationManager = new ObjectRandomLocationManager();
        setBackgroundImage(SECOND_LEVEL_BACKGROUND_IMAGE);
        setEnemies();
        pauseRoot = PauseMenu.getInstance().getRoot();
        try{
            hero = Skeleton.getHero();
        }catch (Exception e){
            e.printStackTrace();
        }
        score = 0;
    }

    public void clearGameObjects(){
        gameObjects.clear();
    }

    public void setBackgroundImage(String backgroundImage){
        this.backgroundImage = new BackgroundImage(new javafx.scene.image.Image("file:\\" + backgroundImage,852,480,false,true),
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

            for(int i = 0; i < 400; i = i + 125) {
                gameObject = new PrisonGuardian(1, i, true);
                gameObjects.add(gameObject);
            }
            for(int i = 800; i < 50000;) {

                int choice = (int) (Math.random() * 18);
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
                } else if (choice < 17) {
                    locationManager.generateLocation(i = i + 50, i + 250, 30, 400);
                    gameObject = new EscapePowerUp(locationManager.getX(), locationManager.getY(), (int) (Math.random() * 2 + 1));
                    gameObject.setVisible(false);
                    gameObjects.add(gameObject);
                } else {
                    locationManager.generateLocation(i = i + 50, i + 250, 30, 400);
                    gameObject = new EscapePowerUp(locationManager.getX(), locationManager.getY(), 3);
                    gameObject.setVisible(false);
                    gameObjects.add(gameObject);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void update() {
        score = score + 0.01;
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
                        head.setFill(Color.WHITE);
                        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                        lastNanoTime = currentNanoTime;

                        gc.clearRect(0, 0, 850,480);
                        hero.update(elapsedTime);
                        hero.draw(gc);
                        hero.controlSubmarine();

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

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Parent load(){
        root.getStylesheets().add("sample/UserInterface/Screen/style.css");
        createContent();
        head = new Text("Score: " + (int)score);
        head.setTranslateX(350);
        head.setTranslateY(-200);
        head.setId("header-help");
        if(t == null){
            t = new Thread(this);
            t.start();
        }
        ((GridPane)root).getChildren().add(head);
        return root;
    }

    public static void setMap(EscapeMap map) {
        EscapeMap.map = map;
    }

    public void setScore(int score) {
        this.score = this.score + score;
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
    public ArrayList<PrisonGuardian> getVisibleGuardians(){
        ArrayList <PrisonGuardian> guardians = new ArrayList<>();
        int count = gameObjects.size();
        for(int i = 0; i < count; i++){
            if(gameObjects.get(i) != null)
                if(gameObjects.get(i).isVisible())
                    if(gameObjects.get(i).toString().equals("Guardian"))
                        guardians.add((PrisonGuardian)gameObjects.get(i));
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

    public static EscapeMap getEscapeMap(){
        if(map == null)
            map = new EscapeMap();
        return map;
    }

    public Skeleton getHero() {
        return hero;
    }

    public ArrayList<EscapePowerUp> getVisiblePowerUps(){
        ArrayList <EscapePowerUp> powerUps = new ArrayList<>();
        int count = gameObjects.size();
        for(int i = 0; i < count; i++){
            if(gameObjects.get(i) != null)
                if(gameObjects.get(i).isVisible())
                    if(gameObjects.get(i).toString().equals("Power Up"))
                        powerUps.add((EscapePowerUp) gameObjects.get(i));
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