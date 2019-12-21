package sample.ApplicationLogic.GameEntities;

import java.io.FileNotFoundException;

public class HunterSkillBuilder extends AbstarctSkillBuilder {

    //Constructor
    public HunterSkillBuilder() throws FileNotFoundException {
        skills = new Skill[2];
        skills[0] = new Skill(4, 5);
        skills[1] = new Skill(5, 5);
    }

    //Methods
    public void update(double time) throws FileNotFoundException {
        for( Skill skill: skills){
            skill.update( time, null);
        }
    }
}