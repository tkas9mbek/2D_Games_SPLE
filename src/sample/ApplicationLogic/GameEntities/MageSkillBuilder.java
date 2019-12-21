package sample.ApplicationLogic.GameEntities;

import java.io.FileNotFoundException;

public class MageSkillBuilder extends AbstarctSkillBuilder{

    //Attributes
    int subLvl;

    //Constructor
    public MageSkillBuilder(int lvl) throws FileNotFoundException {
        subLvl = lvl;
        skills = new Skill[3];
        skills[0] = new Skill(1, subLvl);
        skills[1] = new Skill(2, subLvl);
        skills[2] = new Skill(3, subLvl);
    }

    //Methods
    public void update(double time, Mage sub) throws FileNotFoundException {
        if( sub.getSubLevel() != subLvl){
            subLvl = sub.getSubLevel();
        }
        for( Skill skill: skills){
            skill.update( time, sub);
        }
    }

    public Skill getSkill( int ID){
        if( skills[ID - 1].isUnlocked() ){
            return skills[ID - 1];
        }
        return null;
    }
}