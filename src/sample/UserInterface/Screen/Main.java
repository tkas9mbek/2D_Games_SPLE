package sample.UserInterface.Screen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.ApplicationLogic.GameManagement.SoundEngine;
import sample.UserInterface.InputManagement.InputManager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application implements EventHandler<ActionEvent>{
    private final String CONFIGURATION_FILE = System.getProperty("user.dir") +  "\\src\\sample\\configuration.txt";

    private static Parent root;
    //private static Parent root1;
    private static ScreenManager sm;
    private static Stage primaryStage;
    private static Scene menuscene;
    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    public static Parent getRoot() {
        return root;
    }

    private static Main m;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //HighScorePane y = HighScorePane.getInstance();
        sm = ScreenManager.getInstance(852,480,root);
        root = sm.getRoot();
        //root1 = sm.getRoot();
        this.primaryStage = primaryStage;
        //Header
        File file = new File(CONFIGURATION_FILE);
        Scanner sc = new Scanner(file);
        String game = sc.next();
        System.out.println(game);

        Text header = new Text(game + " Game by cos9");
        header.setId("header-string");
        header.setTranslateX(200);
        header.setTranslateY(50);
        //e -> primaryStage.getScene().setRoot(sm.update(playbutton.getText()))
        //Buttons
        //Buttons

        Button playbutton = new Button("Play " + game);
        playbutton.setTranslateX(335);
        playbutton.setTranslateY(175);
        playbutton.getStyleClass().add("menu-button");
        playbutton.setOnAction(this);

        Button exitbutton = new Button("Quit Game");
        exitbutton.setTranslateX(335);
        exitbutton.setTranslateY(250);
        exitbutton.getStyleClass().add("menu-button");
        exitbutton.setOnAction(this);


        ((GridPane)root).getChildren().addAll(playbutton, header, exitbutton);

        Scene x = new Scene(root);
        this.primaryStage.setScene(x);
        new Thread(new InputManager()).start();
        SoundEngine.getInstance().startMusic();
        this.primaryStage.show();
    }
    public static GridPane getPane(){
        return (GridPane)root;
    }
    public static Scene getMenuScene() {
        return menuscene;
    }

    @Override
    public void handle(ActionEvent e){
        Parent root1 = sm.update(((Button)(e.getSource())).getText());
        if(((Button)(e.getSource())).getText().equals("Quit Game")){
            primaryStage.close();
            System.exit(0);
        }
        primaryStage.getScene().setRoot(root1);
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
    public static Main getInstance(){
        if(m == null)
            m = new Main();
        return m;
    }

    public static void main(String[] args) { launch(args); }
}
