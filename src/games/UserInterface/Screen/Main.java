package games.UserInterface.Screen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import games.ApplicationLogic.GameManagement.SoundEngine;
import games.UserInterface.InputManagement.InputManager;
import java.io.IOException;

public class Main extends Application implements EventHandler<ActionEvent>{
    private static Parent root;
    private static ScreenManager sm;
    private static Stage primaryStage;
    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }
    public static Parent getRoot() {
        return root;
    }
    private static Main m;

    @Override
    public void start(Stage primaryStage) throws Exception{

        sm = ScreenManager.getInstance(852,480,root);
        root = sm.getRoot();
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

        Button exitButton = new Button("Quit Game");
        exitButton.setTranslateX(350);
        exitButton.setTranslateY(330);
        exitButton.setMinWidth(125);
        exitButton.getStyleClass().add("menu-button");
        exitButton.setOnAction(this);


        ((GridPane)root).getChildren().addAll(playShooterButton, playEscapeButton, playQuestButton, header, exitButton, help);

        Scene x = new Scene(root);
        Main.primaryStage.setScene(x);
        new Thread(new InputManager()).start();
        SoundEngine.getInstance().startMusic();
        Main.primaryStage.show();
    }
    public static GridPane getPane(){
        return (GridPane)root;
    }

    @Override
    public void handle(ActionEvent e){
        Parent root1 = null;

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
