package games.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;

abstract public class AbstractSkillBuilder {

    //Attributes
    public Skill skills[];

    //Methods
    public void draw(GraphicsContext gc){
        for( Skill skill: skills){
            skill.draw( gc);
        }
    }

    abstract public Skill getSkill( int ID);
}