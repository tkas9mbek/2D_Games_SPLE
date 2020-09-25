package sample.ApplicationLogic.GameEntities;

import java.util.Random;

public class ObjectRandomLocationManager {

    //Attributes
    private double x;
    private double y;


    //for getting random. how big our screen is
    private double rangeMinX;
    private double rangeMinY;

    private double rangeMaxX;
    private double rangeMaxY;

    //Constructor
    public ObjectRandomLocationManager() {
        rangeMaxY = 480;
        rangeMinY = 0;
        //Generate location.
    }

    //Methods
    public void generateLocation(double xmin, double xmax, double ymin, double ymax) {
        Random rand = new Random();

        double x = xmin + (xmax - xmin) * rand.nextDouble();
        double y = ymin + (ymax - ymin) * rand.nextDouble();

        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRangeMinX() {
        return rangeMinX;
    }

    public void setRangeMinX(double rangeMinX) {
        this.rangeMinX = rangeMinX;
    }

    public double getRangeMinY() {
        return rangeMinY;
    }

    public void setRangeMinY(double rangeMinY) {
        this.rangeMinY = rangeMinY;
    }

    public double getRangeMaxX() {
        return rangeMaxX;
    }

    public void setRangeMaxX(double rangeMaxX) {
        this.rangeMaxX = rangeMaxX;
    }

    public double getRangeMaxY() {
        return rangeMaxY;
    }

    public void setRangeMaxY(double rangeMaxY) {
        this.rangeMaxY = rangeMaxY;
    }
}

