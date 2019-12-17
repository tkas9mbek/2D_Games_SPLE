package sample.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Skill {

    private static final String LOCKED_IMAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\lock.png";
    private static final String COOLDOWN_IMAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\time.png";
    private static final String SKILL1_IMAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\diamond.png";
    private static final String SKILL2_IMAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\fire.png";
    private static final String SKILL3_IMAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\shield.png";
    private static final String SHIELDED = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\shielded.png";
    private static final String MAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\mage.png";

    private double maxEffectTime;
    private double maxCooldownTime;

    //delegation pattern
    private GameObject object;
    private double timeOfEffect;
    private double cooldownTime;
    private int finalAttack;
    private int skillID;
    private boolean unlocked;
    private boolean onEffect;
    private boolean onCooldown;
    private int energyCost;
    private int subLvl;

    //Constructor
    public Skill(int ID, int subLvl) throws FileNotFoundException {
        setSkillID( ID);
        object = new GameObject(15 + 57 * (ID - 1), 428);
        object.setSpriteImage(new Image( new FileInputStream(LOCKED_IMAGE)));
        updateStats(subLvl);
        setTimeOfEffect(0);
        setCooldownTime(0);
        setOnEffect();
    }

    //Methods
    private void updateStats( int subLvl) {
        this.subLvl = subLvl;
        if( skillID == 1){
            maxEffectTime = 10;
            maxCooldownTime = 25;
            setEnergyCost(30);
        }
        if( skillID == 2){
            maxEffectTime = 9;
            maxCooldownTime = 32;
            setEnergyCost(30);
        }
        if( skillID == 3){
            maxEffectTime = 8;
            maxCooldownTime = 28;
            setEnergyCost(40);
        }
        setUnlocked(subLvl);
    }

    public void waterFireballs(Mage sub) throws FileNotFoundException {
        useSkill();
        finalAttack = sub.getAttackDamage();
        sub.setAmountOfProjectile(subLvl + 1);
        sub.setAttackDamage(11);
        setOnEffect();
        setTimeOfEffect( maxEffectTime);
    }

    public void speedBooster(Mage sub) throws FileNotFoundException {
        useSkill();
        sub.setAttackSpeed( 0.4 );
        setOnEffect();
        setTimeOfEffect( maxEffectTime);
    }
    public void invulnerability(Mage sub) throws FileNotFoundException {
        useSkill();
        setOnEffect();
        setTimeOfEffect( maxEffectTime);
        sub.setSpriteImage( new Image( new FileInputStream(SHIELDED)));
    }

    public void useSkill() throws FileNotFoundException {
        setCooldownTime( maxCooldownTime);
        object.setSpriteImage( new Image( new FileInputStream(COOLDOWN_IMAGE)));
    }


    public int getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(int energyCost) {
        if( energyCost >= 0)
            this.energyCost = energyCost;
    }

    public double getTimeOfEffect() {
        return timeOfEffect;
    }

    public void setTimeOfEffect(double timeOfEffect) {
        if( timeOfEffect < 0){
            this.timeOfEffect = 0;
        }
        else
            this.timeOfEffect = timeOfEffect;
    }

    public void updateTimeEffect(double time, Mage sub) throws FileNotFoundException {
        setTimeOfEffect( timeOfEffect - time);
        if( timeOfEffect <= 0){
            if( skillID == 1){
                sub.setAmountOfProjectile( 1);
                sub.setAttackDamage(finalAttack);
                object.setSpriteImage( new Image( new FileInputStream(COOLDOWN_IMAGE)));
            }
            if( skillID == 2){
                sub.setAttackSpeed( 0.8);
                object.setSpriteImage( new Image( new FileInputStream(COOLDOWN_IMAGE)));
            }
            if( skillID == 3){
                object.setSpriteImage( new Image( new FileInputStream(COOLDOWN_IMAGE)));
                sub.setSpriteImage( new Image( new FileInputStream(MAGE)));
            }
        }
    }

    public double getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(double cooldownTime) {
        if( cooldownTime < 0){
            this.cooldownTime = 0;
        }
        else
            this.cooldownTime = cooldownTime;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        if( skillID == 1 || skillID == 2 || skillID == 3) {
            this.skillID = skillID;
        }
    }

    public boolean isOnEffect() {
        return timeOfEffect > 0;
    }

    public void setOnEffect() {
        onEffect = timeOfEffect > 0;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int subLevel) {
        unlocked = subLevel >= skillID;
    }

    public void draw(GraphicsContext g){
        object.draw( g);
    }

    public void update( double time, Mage sub) throws FileNotFoundException {
        if( sub.getSubLevel() != subLvl){
            updateStats( sub.getSubLevel());
        }
        if( isOnEffect()){
            updateTimeEffect( time, sub);
        }
        if( !isOnCooldown()){
            restoreImages();
        }else{
            cooldownTime -= time;
            setOnCooldown();
        }
    }

    private void restoreImages() throws FileNotFoundException {
        if( isUnlocked() ){
            if( skillID == 1){
                object.setSpriteImage( new Image( new FileInputStream(SKILL1_IMAGE)));
            }
            if( skillID == 2){
                object.setSpriteImage( new Image( new FileInputStream(SKILL2_IMAGE)));
            }
            if( skillID == 3){
                object.setSpriteImage( new Image( new FileInputStream(SKILL3_IMAGE)));
            }
        }
    }

    public boolean isOnCooldown() {
        setOnCooldown();
        return onCooldown;
    }

    public void setOnCooldown() {
        onCooldown = cooldownTime > 0;
    }
}