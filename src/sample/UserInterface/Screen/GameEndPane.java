package sample.UserInterface.Screen;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import sample.ApplicationLogic.GameEntities.Map;

/**
 * Created by Okur on 03/05/18.
 */
public class GameEndPane {
    private GridPane gridPaneGE;
    private Scene sceneGE;

    private int score;
    private static GameEndPane gameEndPane;

    private GameEndPane(int score){
        this.score = score;

        double buttonWidth = 125;
        double buttonHeight = 10;

        final Button butBack = new Button("Back");
        final Text gameOver = new Text("You Have Won");
        final Text scoreText = new Text("Score: " + this.score);

        gameOver.setScaleX(8);
        gameOver.setScaleY(8);

        scoreText.setScaleX(3);
        scoreText.setScaleY(3);

        butBack.setPrefWidth(buttonWidth);
        butBack.setPrefHeight(buttonHeight);

        /**
         * Back Button
         */

        butBack.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Main.getPrimaryStage().setScene(Main.getPrimaryStage().getScene());

            }
        });

        gridPaneGE = new GridPane();

        gridPaneGE.setHgap(0);
        gridPaneGE.setVgap(0);

        gridPaneGE.getColumnConstraints().add(new ColumnConstraints(10));
        gridPaneGE.getColumnConstraints().add(new ColumnConstraints(370));
        gridPaneGE.getColumnConstraints().add(new ColumnConstraints(72));
        gridPaneGE.getColumnConstraints().add(new ColumnConstraints(370));
        gridPaneGE.getRowConstraints().add(new RowConstraints(120));
        gridPaneGE.getRowConstraints().add(new RowConstraints(120));
        gridPaneGE.getRowConstraints().add(new RowConstraints(120));
        gridPaneGE.getRowConstraints().add(new RowConstraints(120));

        gridPaneGE.add(butBack,1,3);
        gridPaneGE.add(gameOver,2,1);
        gridPaneGE.add(scoreText,2,2);



        final Image backgroundImage = new Image (GameOverPane.class.getResource("Settings_Background.bmp").toString(),852,480,false,false);

        final BackgroundImage backgi = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        final Background backg = new Background(backgi);

        gridPaneGE.setBackground(backg);
        gridPaneGE.setPrefSize(852,480);
        //gridPaneGE.setGridLinesVisible(true);

        sceneGE = new Scene(gridPaneGE,852,480);
    }

    /**
     *
     * @return this.Pane
     */
    public GridPane getPane(){ return gridPaneGE; }

    public Scene getScene(){ return sceneGE; }

    /**
     * Set score
     * @param i = score that will be displayed.
     */
    public void setScore(int i) {
        this.score = i;
    }
    public static GameEndPane getInstance(){
        if(gameEndPane == null){
            gameEndPane = new GameEndPane(Map.getMap().getScore());
        }
        return gameEndPane;
    }
}
