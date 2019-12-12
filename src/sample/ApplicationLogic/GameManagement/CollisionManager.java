package sample.ApplicationLogic.GameManagement;

import sample.ApplicationLogic.GameEntities.GameObject;

public class CollisionManager {
    private static CollisionManager cm;
    private GameObject object1;
    private GameObject object2;
    private CollisionManager(){  }
    public boolean checkGameObjectCollision(GameObject g1, GameObject g2){
        object1 = g1;
        object2 = g2;
        double xPosOfEnemy = g1.getCollisionRectangle().getX();
        //System.out.println(xPosOfEnemy);
        double yPosOfEnemy = g1.getCollisionRectangle().getY();
        //System.out.println(yPosOfEnemy);
        double widthOfEnemy = g1.getCollisionRectangle().getWidth();
        //System.out.println(widthOfEnemy);
        double heightOfEnemy = g1.getCollisionRectangle().getHeight();
        //System.out.println(heightOfEnemy);
        if(g2.getCollisionRectangle().intersects(xPosOfEnemy,yPosOfEnemy,widthOfEnemy,heightOfEnemy)){
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
