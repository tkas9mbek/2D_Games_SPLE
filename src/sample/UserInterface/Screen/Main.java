package sample.UserInterface.Screen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.ApplicationLogic.GameManagement.SoundEngine;
import sample.UserInterface.InputManagement.InputManager;
import java.io.IOException;

public class Main extends Application implements EventHandler<ActionEvent>{
    private static Parent root;
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
        Main.primaryStage = primaryStage;

        //Header
        Text header = new Text("2D Games by cos9");
        header.setId("header-string");
        header.setTranslateX(200);
        header.setTranslateY(50);

        Text help = new Text("arrows: move\n" +
                "space: shoot\n" +
                "z, x, c: skills");
        help.setId("header-help");
        help.setTranslateX(525);
        help.setTranslateY(235);

        Button playShooterButton = new Button("Play Shooter");
        playShooterButton.setTranslateX(350);
        playShooterButton.setTranslateY(150);
        playShooterButton.setMinWidth(125);
        playShooterButton.getStyleClass().add("menu-button");
        playShooterButton.setOnAction(this);

        Button playEscapeButton = new Button("Play Escape");
        playEscapeButton.setTranslateX(350);
        playEscapeButton.setTranslateY(210);
        playEscapeButton.setMinWidth(125);
        playEscapeButton.getStyleClass().add("menu-button");
        playEscapeButton.setOnAction(this);

        Button playQuestButton = new Button("Play Quest");
        playQuestButton.setTranslateX(350);
        playQuestButton.setTranslateY(270);
        playQuestButton.setMinWidth(125);
        playQuestButton.getStyleClass().add("menu-button");
        playQuestButton.setOnAction(this);

        Button exitbutton = new Button("Quit Game");
        exitbutton.setTranslateX(350);
        exitbutton.setTranslateY(330);
        exitbutton.setMinWidth(125);
        exitbutton.getStyleClass().add("menu-button");
        exitbutton.setOnAction(this);


        ((GridPane)root).getChildren().addAll(playShooterButton, playEscapeButton, playQuestButton, header, exitbutton, help);

        Scene x = new Scene(root);
        Main.primaryStage.setScene(x);
        new Thread(new InputManager()).start();
        SoundEngine.getInstance().startMusic();
        Main.primaryStage.show();
    }
    public static GridPane getPane(){
        return (GridPane)root;
    }
    public static Scene getMenuScene() {
        return menuscene;
    }

    @Override
    public void handle(ActionEvent e){
        Parent root1 = null;
        System.out.println(((Button)(e.getSource())).getText());
        try {
            root1 = sm.update(((Button)(e.getSource())).getText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
