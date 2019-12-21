package sample.ApplicationLogic.GameManagement;

import java.io.FileNotFoundException;

public class EngineFactory {
    private ShooterEngine shooterEngine;
    private EscapeEngine escapeEngine;
    private QuestEngine questEngine;

    public ShooterEngine getShooterEngine() {
        if (null == shooterEngine) {
            shooterEngine = new ShooterEngine();
        }
        return shooterEngine;
    }

    public EscapeEngine getEscapeEngine() {
        if (null == escapeEngine) {
            escapeEngine = new EscapeEngine();
        }
        return escapeEngine;
    }

    public QuestEngine getQuestEngine() throws FileNotFoundException {
        if (null == questEngine) {
            questEngine = new QuestEngine();
        }
        return questEngine;
    }
}