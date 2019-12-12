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

import java.net.URL;

//import java.awt.*;

public class Main extends Application implements EventHandler<ActionEvent>{
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
        Text header = new Text("cos9 presents");
        header.setId("header-string");
        header.setTranslateX(250);
        header.setTranslateY(50);
        //e -> primaryStage.getScene().setRoot(sm.update(playbutton.getText()))
        //Buttons
        Button playbutton = new Button("Play Shooter");
        System.out.println(playbutton.getText());
        playbutton.setTranslateX(315);
        playbutton.setTranslateY(125);
        playbutton.getStyleClass().add("menu-button");
        playbutton.setOnAction(this);

        Button survivalbutton = new Button("Play Escape");
        survivalbutton.setTranslateX(315);
        survivalbutton.setTranslateY(200);
        survivalbutton.getStyleClass().add("menu-button");
        survivalbutton.setOnAction(this);

        Button settingsbutton = new Button("Play Quest");
        settingsbutton.setTranslateX(315);
        settingsbutton.setTranslateY(275);
        settingsbutton.getStyleClass().add("menu-button");
        settingsbutton.setOnAction(this);


        Button exitbutton = new Button("Quit Game");
        exitbutton.setTranslateX(315);
        exitbutton.setTranslateY(365);
        exitbutton.getStyleClass().add("menu-button");
        exitbutton.setOnAction(this);


        ((GridPane)root).getChildren().addAll(playbutton, survivalbutton, header, settingsbutton, exitbutton);

        Scene x = new Scene(root);
        //menuscene = x;
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
        try {
            Parent root1 = sm.update(((Button)(e.getSource())).getText());
            if(((Button)(e.getSource())).getText().equals("Quit Game")){
                primaryStage.close();
                System.exit(0);
            }
            primaryStage.getScene().setRoot(root1);
            //root = sm.update(((Button)(e.getSource())).getText());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

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
