package sample.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Experience {
    private static final int imageSize = 8;
    private static final int maxLvl = 5;
    private static final int expCap = 1000;
    private int maxExperience;
    private int curExperience;
    private int subLevel;
    private Image[] EXPBAR;
    private Image[] LEVEL;
    private GameObject expBar;
    private GameObject lvlBar;

    public Experience(int sLvl) throws FileNotFoundException {
        EXPBAR = new Image[imageSize];
        LEVEL = new Image[maxLvl + 1];
        for(int i = 0; i < imageSize; i++ ){
            EXPBAR[i] = new Image(new FileInputStream(System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\exp_" + i + ".png"));
        }
        for( int i = 0; i <= maxLvl; i++){
            LEVEL[i] = new Image(new FileInputStream(System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\level_" + i + ".png"));
        }
        lvlBar = new GameObject( 14, 49);
        expBar = new GameObject( 14, 49);
        updateStats( sLvl);
        expBar.setSpriteImage( EXPBAR[ (curExperience - (maxExperience / (2 * (imageSize - 1)) ) / (maxExperience / (imageSize - 1) ) )] );
    }

    private void updateStats( int sLvl) {
        setMaxExperience(expCap / 2 + expCap * subLevel);
        setCurExperience(0);
        lvlBar.setSpriteImage( LEVEL[sLvl]);
    }

    public int getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(int maxExperience) {
        if( maxExperience >= 0) {
            this.maxExperience = maxExperience;
        }
        else
            this.maxExperience = 0;
    }

    public int getCurExperience() {
        return curExperience;
    }

    public void setCurExperience(int curExperience) {
        if( curExperience <= 0) {
            this.curExperience = 0;
        } else if( curExperience >= maxExperience){
            this.curExperience = maxExperience;
        }
        else
            this.curExperience = curExperience;
    }

    public void setSubLevel(int subLevel) {
        if( subLevel >= 0)
            this.subLevel = subLevel;
        else
            throw new IndexOutOfBoundsException("Not valid subLevel value");
    }

    public void update( int increaseAmount, Hero sub) throws FileNotFoundException {
        subLevel = sub.getSubLevel();
        setCurExperience( curExperience + increaseAmount);
        expBar.setSpriteImage( EXPBAR[ (curExperience + 1 ) / (maxExperience / (imageSize - 1) )]);
        System.out.println(curExperience);
        System.out.println(maxExperience);
        if( curExperience >= maxExperience && subLevel != maxLvl){
            System.out.println("entered here");
            sub.setSubLevel( subLevel + 1);
            sub.updateStats();
            updateStats( subLevel + 1);
            //lvlBar.setSpriteImage(LEVEL[subLevel + 1]);
        }
    }

    public void draw(GraphicsContext gc)
    {
        expBar.draw(gc);
        lvlBar.draw(gc);
    }
}

