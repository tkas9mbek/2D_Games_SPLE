package games.ApplicationLogic.GameEntities;

import java.io.FileNotFoundException;

public class HunterSkillBuilder extends AbstractSkillBuilder {

    //Constructor
    public HunterSkillBuilder() throws FileNotFoundException {
        skills = new Skill[2];
        skills[0] = new Skill(4 );
        skills[1] = new Skill(5 );
    }

    //Methods
    public void update(double time) throws FileNotFoundException {
        for( Skill skill: skills){
            skill.update( time);
        }
    }

    public Skill getSkill( int ID){
        return skills[ID - 1];
    }
}