package sample.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;

import java.io.FileNotFoundException;

public class SkillManager {

    //Attributes
    private Skill skills[];
    int subLvl;

    //Constructor
    public SkillManager( int lvl) throws FileNotFoundException {
        subLvl = lvl;
        skills = new Skill[3];
        skills[0] = new Skill(1, subLvl);
        skills[1] = new Skill(2, subLvl);
        skills[2] = new Skill(3, subLvl);
    }

    //Methods
    public void update(double time, Hero sub) throws FileNotFoundException {
        if( sub.getSubLevel() != subLvl){
            subLvl = sub.getSubLevel();
        }
        for( Skill skill: skills){
            skill.update( time, sub);
        }
    }

    public  void draw(GraphicsContext gc){
        for( Skill skill: skills){
            skill.draw( gc);
        }
    }

    public Skill getSkill( int ID){
        if( skills[ID - 1].isUnlocked() ){
            return skills[ID - 1];
        }
        return null;
    }
}