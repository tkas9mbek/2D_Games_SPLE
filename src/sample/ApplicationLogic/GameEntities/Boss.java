package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import javax.swing.plaf.SeparatorUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Boss extends BigEnemy {

    private final String SKELETON = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\skeleton_boss.png";

    private double abilityCooldown;
    private double currentCooldown;
    private double abilityDamage;
    private ArrayList<Bullet> bullets;

    public Boss() {
    }

    @Override
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Boss(double xPos, double yPos, int mapLvl) throws FileNotFoundException {
        super(xPos, yPos);

        // setting boss image
        setSpriteImage(new Image(new FileInputStream(SKELETON)));

        setVelocity(-10, -70);

        setHealth(100 + 250 * mapLvl);
        setCollisionDmg(100);
        setExperiencePrize(150 + 200 * mapLvl);
        setScorePrize( 150 + 200 * mapLvl);
        setAttackDamage(10 + 5 * mapLvl);
        setAmountOfProjectile( 1 + 2 * mapLvl);
        setAttackSpeed(1);
        setAbilityCooldown(3);
        setAbilityDamage( 27 + 5 * mapLvl);
        setType("Boss");
        bullets = new ArrayList<>();
    }

    public double getAbilityCooldown() {
        return abilityCooldown;
    }

    public void setAbilityCooldown(double abilityCooldown) {
        if( abilityCooldown >= 0) {
            this.abilityCooldown = abilityCooldown;
            currentCooldown = abilityCooldown;
        }
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    public double getCurrentCooldown() {
        return currentCooldown;
    }

    public double getAbilityDamage() {
        return abilityDamage;
    }

    public void setAbilityDamage(double abilityDamage) {
        if( abilityDamage >= 0)
            this.abilityDamage = abilityDamage;
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    /*  index 0 = damage of the bullets
        index 1 = ID of bullets
        index 2 = x position of bullets
        index 3 = y position of bullets
     */
    public void useAbility(){
        if( currentCooldown <= 0 && isVisible())
        {
            try{
                bullets.add(new Bullet(getXPos(), getYPos() + getSpriteImage().getHeight() / 2 , (int)getAbilityDamage(), 2));
                currentCooldown = abilityCooldown;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void update(double time){
        super.update(time);
        if( currentCooldown > 0)
            currentCooldown -= time;
    }
}
