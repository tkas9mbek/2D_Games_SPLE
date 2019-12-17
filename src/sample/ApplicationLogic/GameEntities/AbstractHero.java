package sample.ApplicationLogic.GameEntities;

abstract public class AbstractHero extends GameObject {
    public AbstractHero(int x, int y) {
        super(x, y);
    }
    abstract public void controlHero();
}