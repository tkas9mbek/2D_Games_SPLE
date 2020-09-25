package sample.ApplicationLogic.GameEntities;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class QuestMap extends AbstractMap {
    private ArrayList<String> quests;
    private ArrayList<String> inventory;
    private String hint;
    private int mapNo;
    private Text head;
    private final String SECOND_LEVEL_BACKGROUND_IMAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\forest.jpg";
    private Thread t;
    private BackgroundImage backgroundImage;
    private Hunter hero;

    public void run(){
    }

    public QuestMap(ArrayList<String> quests, ArrayList<String> inventory, int mapNo) throws FileNotFoundException {
        super();
        root = new GridPane();
        gameObjects = new ArrayList<>();
        setBackgroundImage(SECOND_LEVEL_BACKGROUND_IMAGE);
        this.mapNo = mapNo;
        this.quests = quests;
        this.inventory = inventory;
        try{
            hero = heroFactory.getHunter();
        }catch (Exception e){
            e.printStackTrace();
        }
        setEnemies();
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

            if(mapNo == 1) {
                // adding home
                gameObject = new Neutral(570, 295, 2, "Return to home after completing quests");
                gameObjects.add(gameObject);

                // adding shaman
                gameObject = new Neutral(250, 175, 1, "Last time a lumberjack was seen in the west");
                gameObjects.add(gameObject);

                // adding trees
                gameObject = new Obstacle(770, 0, true, 0);
                gameObjects.add(gameObject);
                gameObject = new Obstacle(770, 130, true, 0);
                gameObjects.add(gameObject);
                gameObject = new Obstacle(770, 260, true, 0);
                gameObjects.add(gameObject);
                gameObject = new Obstacle(770, 360, true, 0);
                gameObjects.add(gameObject);
            } else if(mapNo == 2) {
                gameObject = new BigEnemy(140, 50, 0);
                gameObjects.add(gameObject);
                gameObject = new Obstacle(0, 0, true, 0);
                gameObjects.add(gameObject);
                gameObject = new Obstacle(0, 110, true, 0);
                gameObjects.add(gameObject);
                gameObject = new Obstacle(0, 215, true, 0);
                gameObjects.add(gameObject);
                gameObject = new Obstacle(0, 310, true, 0);
                gameObjects.add(gameObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void update() {}

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
                        String text = "";

                        if(quests.size() > 0) {
                            text = "QUESTS:  ";
                            for(String q: quests) {
                                text = text.concat(q + "\n");
                            }
                        }

                        if(inventory.size() > 0) {
                            text = text.concat("INVENTORY:  ");
                            for(String i: inventory) {
                                text = text.concat(i + "\n");
                            }

                        }

                        if( hint != null) {
                            text = text.concat("HINT: " + hint + "\n");
                        }

                        head.setText(text);
                        head.setId("score-text");

                        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                        lastNanoTime = currentNanoTime;

                        gc.clearRect(0, 0, 850,480);
                        hero.update(elapsedTime);
                        hero.draw(gc);
                        hero.controlHero();

                        for(int k = 0; k < hero.getBullets().size(); k++){
                            hero.getBullets().get(k).update(elapsedTime);
                            hero.getBullets().get(k).draw(gc);
                        }

                        for (GameObject gameObject : gameObjects) {
                            gameObject.update(elapsedTime);
                            gameObject.draw(gc);

                            if (gameObject.toString().equals("Big Enemy")) {
                                ((BigEnemy) gameObject).shoot();
                                for (int j = 0; j < ((BigEnemy) gameObject).getBullets().size(); j++) {
                                    ((BigEnemy) gameObject).getBullets().get(j).update(elapsedTime);
                                    ((BigEnemy) gameObject).getBullets().get(j).draw(gc);
                                }
                            }
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
        root.getStylesheets().add("sample/UserInterface/Screen/style.css");
        createContent();
        head = new Text("");
        head.setTranslateX(355);
        head.setTranslateY(-190);
        head.setId("header-help");
        if(t == null){
            t = new Thread(this);
            t.start();
        }
        ((GridPane)root).getChildren().add(head);
        return root;
    }

    public GameObject getGameObject(double x, double y){
        GameObject returnval = null;
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getXPos() == x && gameObject.getYPos() == y) {
                returnval = gameObject;
            }
        }
        return returnval;
    }

    public ArrayList<Obstacle> getVisibleObstacles(){
        ArrayList <Obstacle> obstacles = new ArrayList<>();
        int count = gameObjects.size();
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null)
                if (gameObject.isVisible())
                    if (gameObject.toString().equals("Obstacle"))
                        obstacles.add((Obstacle) gameObject);
        }
        return obstacles;
    }

    public ArrayList<Neutral> getVisibleNeutrals(){
        ArrayList <Neutral> obstacles = new ArrayList<>();
        int count = gameObjects.size();
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null)
                if (gameObject.isVisible())
                    if (gameObject.toString().equals("Neutral"))
                        obstacles.add((Neutral) gameObject);
        }
        return obstacles;
    }

    public ArrayList<Neutral> getVisibleHome(){
        ArrayList <Neutral> home = new ArrayList<>();
        int count = gameObjects.size();
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null)
                if (gameObject.isVisible())
                    if (gameObject.toString().equals("Home"))
                        home.add((Neutral) gameObject);
        }
        return home;
    }

    public ArrayList<Item> getVisibleItems(){
        ArrayList <Item> items = new ArrayList<>();
        int count = gameObjects.size();
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null)
                if (gameObject.isVisible())
                    if (gameObject.toString().equals("Item"))
                        items.add((Item) gameObject);
        }
        return items;
    }

    public ArrayList<Enemy> getVisibleEnemies(){
        ArrayList <Enemy> enemies = new ArrayList<>();
        int count = gameObjects.size();
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null)
                if (gameObject.isVisible())
                    if (gameObject.toString().equals("Big Enemy"))
                        enemies.add((Enemy) gameObject);
        }
        return enemies;
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

    public void removeObject(GameObject object) {
        gameObjects.remove(object);
    }

    public void addObject(GameObject object) {
        gameObjects.add(object);
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public ArrayList<String> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<String> quests) {
        this.quests = quests;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public void addToInventory(String item) {
        inventory.add(item);
    }

    public int getMapNo() {
        return mapNo;
    }

    public void setMapNo(int mapNo) {
        this.mapNo = mapNo;
    }

}