package games.ApplicationLogic.GameEntities;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapFactory {
    private EscapeMap escapeMap;
    private ShooterMap shooterMap;
    private QuestMap questMap;

    public EscapeMap getEscapeMap() throws FileNotFoundException {
        if (null == escapeMap) {
            escapeMap = new EscapeMap();
        }
        return escapeMap;
    }

    public ShooterMap getShooterMap() throws FileNotFoundException {
        if (null == shooterMap) {
            shooterMap = new ShooterMap();
        }
        return shooterMap;
    }

    public QuestMap getQuestMap(ArrayList<String> quests, ArrayList<String> inventory, int mapNo) throws FileNotFoundException {
        if (null == questMap) {
            questMap = new QuestMap(quests, inventory, mapNo);
        }
        return questMap;
    }

    public QuestMap getQuestMap() {
        return questMap;
    }
}
