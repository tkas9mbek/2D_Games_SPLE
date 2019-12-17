package sample.ApplicationLogic.GameManagement;

public class EngineFactory {
    private ShooterEngine shooterEngine;
    private EscapeEngine escapeEngine;

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
}
