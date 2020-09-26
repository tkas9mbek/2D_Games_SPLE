package games.ApplicationLogic.GameManagement;

import games.ApplicationLogic.GameEntities.GameObject;

public class CollisionManager {
    private static CollisionManager cm;
    private CollisionManager(){  }
    public boolean checkGameObjectCollision(GameObject g1, GameObject g2){
        double xPosOfEnemy = g1.getCollisionRectangle().getX();
        double yPosOfEnemy = g1.getCollisionRectangle().getY();
        double widthOfEnemy = g1.getCollisionRectangle().getWidth();
        double heightOfEnemy = g1.getCollisionRectangle().getHeight();
        if(g2 != null &&
                g2.getCollisionRectangle().intersects(xPosOfEnemy,yPosOfEnemy,widthOfEnemy,heightOfEnemy)){
            return true;
        }
        return false;
    }
    public static CollisionManager getInstance(){
        if(cm == null){
            cm = new CollisionManager();
        }
        return cm;
    }
}
