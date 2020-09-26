package games.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Skill {

    private static final String PROJECT_DIR = System.getProperty("user.dir") + "\\game-assets\\";
    private static final String LOCKED_IMAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\lock.png";
    private static final String COOLDOWN_IMAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\time.png";
    private static final String SKILL1_IMAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\toxin.png";
    private static final String SKILL2_IMAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\fire.png";
    private static final String SKILL3_IMAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\shield.png";
    private static final String SKILL4_IMAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\multishot.png";
    private static final String SKILL5_IMAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\regeneration.png";
    private static final String SHIELDED = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\shielded.png";
    private static final String MAGE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\mage.png";

    private double maxEffectTime;
    private double maxCooldownTime;

    //delegation pattern
    private final GameObject object;
    private double timeOfEffect;
    private double cooldownTime;
    private int finalAttack;
    private int skillID;
    private boolean unlocked;
    private boolean onCooldown;
    private int energyCost;
    private int mageLvl;

    //Constructor
    public Skill(int ID, int mageLvl) throws FileNotFoundException {
        setSkillID( ID);
        object = new GameObject(15 + 57 * ( (ID - 1) % 3), 428);
        object.setSpriteImage(new Image( new FileInputStream(LOCKED_IMAGE)));
        updateStats(mageLvl);
        setTimeOfEffect(0);
        setCooldownTime(0);
        setOnEffect();
    }

    public Skill(int ID) throws FileNotFoundException {
        setSkillID( ID);
        skillID = ID;
        object = new GameObject(15 + 57 * ( (ID - 1) % 3), 428);
        setCooldownTime(0);
        if( skillID == 4){
            object.setSpriteImage( new Image( new FileInputStream(SKILL4_IMAGE)));
            maxCooldownTime = 10;
            setEnergyCost(0);
        }
        if( skillID == 5){
            object.setSpriteImage( new Image( new FileInputStream(SKILL5_IMAGE)));
            maxCooldownTime = 28;
            setEnergyCost(0);
        }
    }

    //Methods
    private void updateStats( int mageLvl) {
        this.mageLvl = mageLvl;
        if( skillID == 1){
            maxEffectTime = 8;
            maxCooldownTime = 20;
            setEnergyCost(30);
        }
        if( skillID == 2){
            maxEffectTime = 10;
            maxCooldownTime = 22;
            setEnergyCost(20);
        }
        if( skillID == 3){
            maxEffectTime = 8;
            maxCooldownTime = 28;
            setEnergyCost(25);
        }
        setUnlocked(mageLvl);
    }

    public void waterFireballs(Mage mage) throws FileNotFoundException {
        useSkill();
        finalAttack = mage.getAttackDamage();
        mage.setAmountOfProjectile(3);
        mage.setAttackDamage(mageLvl * 3 + 6);
        setOnEffect();
        setTimeOfEffect( maxEffectTime);
    }

    public void speedBooster(Mage mage) throws FileNotFoundException {
        useSkill();
        mage.setAttackSpeed( 0.33 );
        setOnEffect();
        setTimeOfEffect( maxEffectTime);
    }
    public void invulnerability(Mage mage) throws FileNotFoundException {
        useSkill();
        setOnEffect();
        setTimeOfEffect( maxEffectTime);
        mage.setSpriteImage( new Image( new FileInputStream(SHIELDED)));
    }

    public void multishot(Hunter hunter) throws FileNotFoundException {
        useSkill();
        hunter.setMultishot(2);
    }

    public void regeneration(Hunter hunter) throws FileNotFoundException {
        useSkill();
        hunter.regenHealth(35);
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

    public void updateTimeEffect(double time, Mage mage) throws FileNotFoundException {
        setTimeOfEffect( timeOfEffect - time);
        if( timeOfEffect <= 0){
            if( skillID == 1){
                mage.setAmountOfProjectile( 1);
                mage.setAttackDamage(finalAttack);
            }
            if( skillID == 2){
                mage.setAttackSpeed( 0.8);
            }
            if( skillID == 3){
                mage.setSpriteImage( new Image( new FileInputStream(MAGE)));
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
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int mageLevel) {
        unlocked = mageLevel >= skillID;
    }

    public void draw(GraphicsContext g){
        object.draw( g);
    }

    public void update( double time, Mage mage) throws FileNotFoundException {
        if (mage.getSubLevel() != mageLvl) {
            updateStats(mage.getSubLevel());
        }
        if( isOnEffect()){
            updateTimeEffect( time, mage);
        }

        if( !isOnCooldown()){
            restoreImages();
        }else{
            cooldownTime -= time;
            setOnCooldown();
        }
    }

    public void update( double time) throws FileNotFoundException {
        if( !isOnCooldown()){
            restoreImages();
        }else{
            cooldownTime -= time;
            setOnCooldown();
        }
    }

    private void restoreImages() throws FileNotFoundException {
        if( skillID == 1){
            if( isUnlocked() ) {
                object.setSpriteImage(new Image(new FileInputStream(SKILL1_IMAGE)));
            }
        }
        if( skillID == 2){
            if( isUnlocked() ) {
                object.setSpriteImage(new Image(new FileInputStream(SKILL2_IMAGE)));
            }
        }
        if( skillID == 3){
            if( isUnlocked() ) {
                object.setSpriteImage(new Image(new FileInputStream(SKILL3_IMAGE)));
            }
        }
        if( skillID == 4){
            object.setSpriteImage( new Image( new FileInputStream(SKILL4_IMAGE)));
        }
        if( skillID == 5){
            object.setSpriteImage( new Image( new FileInputStream(SKILL5_IMAGE)));
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