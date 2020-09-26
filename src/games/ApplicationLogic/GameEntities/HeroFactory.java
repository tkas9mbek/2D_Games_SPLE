package games.ApplicationLogic.GameEntities;

import java.io.FileNotFoundException;

public class HeroFactory {
    private Mage mage;
    private Skeleton skeleton;
    private Hunter hunter;

    public Mage getMage() throws FileNotFoundException {
        if (null == mage) {
            mage = new Mage(1);
        }
        return mage;
    }

    public Skeleton getSkeleton() throws FileNotFoundException {
        if (null == skeleton) {
            skeleton = new Skeleton();
        }
        return skeleton;
    }

    public Hunter getHunter() throws FileNotFoundException {
        if (null == hunter) {
            hunter = new Hunter();
        }
        return hunter;
    }
}
