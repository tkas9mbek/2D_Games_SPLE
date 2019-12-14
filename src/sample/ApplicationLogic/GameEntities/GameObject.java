package sample.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;


public class GameObject {
    private Image spriteImage;
    private double xPos;
    private double yPos;
    private double xVelocity;
    private double yVelocity;
    private boolean visible;
    private String type;

    // constructor with positions
    GameObject( double xPos, double yPos){
        setLocation(xPos, yPos);
        setVelocity(0, 0);
        if( xPos <= 850 && yPos <= 480 && xPos >= 0 && yPos >= 0) {
            visible = true;
        }
        else{
            visible = false;
        }
    }

    // constructor with detailed data
    GameObject(Image image, double xPos, double yPos, double xVelocity, double yVelocity){
        setLocation(xPos, yPos);
        if( xPos <= 850 && yPos <= 480 && xPos >= 0 && yPos >= 0) {
            visible = true;
        }
        else{
            visible = false;
        }
        setVelocity( xVelocity, yVelocity);
        setSpriteImage( image);
    }
    // default constructor
    GameObject(){
    }
    public void draw(GraphicsContext gc)
    {
        gc.drawImage( spriteImage, xPos, yPos );
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible() {
        visible = !visible;
    }

    public void setVisible( boolean visiblilty) {
        visible = visiblilty;
    }

    public Image getSpriteImage( ){
        return spriteImage;
    }
    public void setSpriteImage( Image image){
        spriteImage = image;
    }
    public void disappearAnimation() throws FileNotFoundException {
        //classes will overwrite
    }
    public double getWidth(){
        return spriteImage.getWidth();
    }
    public double getHeight(){
        return spriteImage.getHeight();
    }
    public double getXPos(){
        return xPos;
    }
    public double getYPos(){
        return yPos;
    }
    public double getXVelocity(){
        return xVelocity;
    }
    public double getYVelocity(){
        return yVelocity;
    }
    public void setLocation( double x, double y){
        if( x >= 0)
            xPos = x;
        else
            throw new ArrayIndexOutOfBoundsException("x is invalid value");
        if( y >= 0 && y <= 480)
            yPos = y;
        else
            throw new ArrayIndexOutOfBoundsException("y is invalid value");
    }
    public void setVelocity( double x, double y){
        xVelocity = x;
        yVelocity = y;
    }

    public Rectangle getCollisionRectangle(){
        return new Rectangle( xPos, yPos, spriteImage.getWidth(), spriteImage.getHeight());
    }

    public void update(double time) throws FileNotFoundException {
        if( xPos <= 850 - getWidth() / 2 && !isVisible() )
            setVisible();
        if(isVisible()) {
            xPos += time * xVelocity;
            yPos += time * yVelocity;
        }
        else {
            xPos += time * -50;
        }
        if( ( (getYPos() > 480 - getHeight()) && getYVelocity() > 0) || (getYPos() < 20 && getYVelocity() < 0))
            setVelocity(getXVelocity(), -getYVelocity());
    }

    public String toString(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
