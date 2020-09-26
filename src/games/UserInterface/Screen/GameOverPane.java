package games.UserInterface.Screen;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Created by Okur on 03/05/18.
 */
public class GameOverPane {
    private final String DIR_LOC = "file:/" + System.getProperty("user.dir").replace("\\", "/") +
            "/game-assets/UserInterface/Screen/images/";
    private final GridPane gridPaneGO;
    private final Scene sceneGO;
    private static GameOverPane gameOverPane;

    private GameOverPane(boolean victory){
        gridPaneGO = new GridPane();

        gridPaneGO.setHgap(0);
        gridPaneGO.setVgap(0);

        gridPaneGO.getColumnConstraints().add(new ColumnConstraints(10));
        gridPaneGO.getColumnConstraints().add(new ColumnConstraints(370));
        gridPaneGO.getColumnConstraints().add(new ColumnConstraints(72));
        gridPaneGO.getColumnConstraints().add(new ColumnConstraints(370));
        gridPaneGO.getRowConstraints().add(new RowConstraints(120));
        gridPaneGO.getRowConstraints().add(new RowConstraints(120));
        gridPaneGO.getRowConstraints().add(new RowConstraints(120));
        gridPaneGO.getRowConstraints().add(new RowConstraints(120));

        BackgroundImage endImage;

        if(victory) {
            endImage =new BackgroundImage(new Image(DIR_LOC + "victory.jpg",852,480,false,true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
        } else {
            endImage =new BackgroundImage(new Image(DIR_LOC + "end.png",852,480,false,true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
        }

        gridPaneGO.setBackground(new Background(endImage));
        gridPaneGO.setPrefSize(852,480);
        sceneGO = new Scene(gridPaneGO,852,480);
    }

    /**
     *
     * @return this.Pane
     */
    public Scene getScene(){ return sceneGO; }

    public static GameOverPane getInstance(boolean victory){
        if(gameOverPane == null){
            gameOverPane = new GameOverPane(victory);
        }
        return gameOverPane;
    }
}
