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
import sample.ApplicationLogic.GameEntities.Map;
import sample.ApplicationLogic.GameManagement.GameEngine;
import sample.UserInterface.InputManagement.InputManager;

import java.net.URL;

public class PauseMenu implements EventHandler<ActionEvent> {
    private Parent root;
    private ScreenManager sm = ScreenManager.getInstance(852, 480, root);
    private static PauseMenu pm;
    //private static Stage primaryStage;

    private PauseMenu(){
        root = sm.getRoot();
        //this.primaryStage = primaryStage;
        //Header
        Text header = new Text("Sea Adventures");
        header.setId("header-string");
        header.setTranslateX(250);
        header.setTranslateY(100);
        //e -> primaryStage.getScene().setRoot(sm.update(playbutton.getText()))
        //Buttons
        Button continuebutton = new Button("Continue Game");
        continuebutton.setTranslateX(300);
        continuebutton.setTranslateY(175);
        continuebutton.getStyleClass().add("menu-button");
        continuebutton.setOnAction(this);

        Button settingsbutton = new Button("Settings");
        settingsbutton.setTranslateX(300);
        settingsbutton.setTranslateY(275);
        settingsbutton.getStyleClass().add("menu-button");
        settingsbutton.setOnAction(this);

        Button creditsbutton = new Button("Credits");
        creditsbutton.setTranslateX(300);
        creditsbutton.setTranslateY(325);
        creditsbutton.getStyleClass().add("menu-button");
        creditsbutton.setOnAction(this);

        Button helpbutton = new Button("View Help");
        helpbutton.setTranslateX(300);
        helpbutton.setTranslateY(375);
        helpbutton.getStyleClass().add("menu-button");
        helpbutton.setOnAction(this);

        Button exitbutton = new Button("Return to Main Menu");
        exitbutton.setTranslateX(300);
        exitbutton.setTranslateY(425);
        exitbutton.getStyleClass().add("menu-button");
        exitbutton.setOnAction(this);

        ((GridPane) root).getChildren().addAll(continuebutton, header, settingsbutton, creditsbutton, helpbutton, exitbutton);
        URL url = getClass().getResource(".");
        System.out.println(url.toExternalForm().substring(6));
        //Scene x = new Scene(root);
        //this.primaryStage.setScene(x);
        new Thread(new InputManager()).start();
        //this.primaryStage.show();
    }


    @Override
    public void handle(ActionEvent e) {
        try{
            root = sm.update(((Button) (e.getSource())).getText());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if(((Button) (e.getSource())).getText().equals("Continue Game")){
            GameEngine.getGameEngine().setGamePaused(false);
            Map.getMap().pauseGame(false);
        }
        if (((Button) (e.getSource())).getText().equals("Return to Main Menu")) {
            try{
                Map.getMap().setRoot(null);
                Map.setMap(null);
                GameEngine.getGameEngine().setGameMap(null);
                GameEngine.getGameEngine().setT(null);
                GameEngine.setGameEngine(null);
                ScreenManager.getInstance(852,480,Main.getPane()).setRoot(null);
                ScreenManager.setSm(null);
                Main.getPrimaryStage().getScene().setRoot(Main.getPane());

            } catch (Exception ex){
                ex.printStackTrace();
            }

        }
        else{
            Main.getPrimaryStage().getScene().setRoot(root);
        }
    }

    //public static Stage getPrimaryStage() {
    //    return primaryStage;
    //}
    public static PauseMenu getInstance(){
        if(pm == null){
            pm = new PauseMenu();
        }
        return pm;
    }

    public Parent getRoot() {
        return root;
    }
}
