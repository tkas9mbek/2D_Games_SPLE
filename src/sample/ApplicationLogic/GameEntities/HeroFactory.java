package sample.ApplicationLogic.GameEntities;

import java.io.FileNotFoundException;

public class HeroFactory {
    private Mage mage;
    private Skeleton skeleton;
    private AbstractHero hunter;

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
}
