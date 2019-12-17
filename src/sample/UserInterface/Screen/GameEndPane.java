package sample.UserInterface.Screen;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;

/**
 * Created by Okur on 03/05/18.
 */
public class GameEndPane {
    private GridPane gridPaneGE;
    private Scene sceneGE;
    private static GameEndPane gameEndPane;

    private GameEndPane(){
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

        URL DIR_LOC = getClass().getResource(".");

        BackgroundImage endImage = new BackgroundImage(new Image(DIR_LOC +  "\\images\\end.png",852,480,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        final Background backg = new Background(endImage);

        gridPaneGE.setBackground(backg);
        gridPaneGE.setPrefSize(852,480);

        sceneGE = new Scene(gridPaneGE,852,480);
    }

    /**
     *
     * @return this.Pane
     */
    public GridPane getPane(){ return gridPaneGE; }

    public Scene getScene(){ return sceneGE; }

    public static GameEndPane getInstance(){
        if(gameEndPane == null){
            gameEndPane = new GameEndPane();
        }
        return gameEndPane;
    }
}
