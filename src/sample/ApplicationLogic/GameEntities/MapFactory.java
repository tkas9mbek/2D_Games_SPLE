package sample.ApplicationLogic.GameEntities;

import java.io.FileNotFoundException;

public class MapFactory {
    private EscapeMap escapeMap;
    private ShooterMap shooterMap;

    public EscapeMap getEscapeMap() {
        if (null == escapeMap) {
            escapeMap = new EscapeMap();
        }
        return escapeMap;
    }

    public ShooterMap getShooterMap() {
        if (null == shooterMap) {
            shooterMap = new ShooterMap();
        }
        return shooterMap;
    }
}
