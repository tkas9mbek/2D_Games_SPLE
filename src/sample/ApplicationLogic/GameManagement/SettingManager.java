package sample.ApplicationLogic.GameManagement;

import javafx.scene.input.KeyCode;
import sample.ApplicationLogic.GameManagement.SoundEngine;
import sample.UserInterface.InputManagement.InputManager;

/**
 * Created by Okur on 07/03/18.
 */


public class SettingManager {
    SoundEngine soundEngine;

    //Integers that hold key ID's
    public static KeyCode UP_KEY = KeyCode.UP;
    public static KeyCode DOWN_KEY = KeyCode.DOWN;
    public static KeyCode LEFT_KEY = KeyCode.LEFT;
    public static KeyCode RIGHT_KEY = KeyCode.RIGHT;
    public static KeyCode SHOOTING_KEY = KeyCode.SPACE;
    public static KeyCode SKILL1_KEY = KeyCode.Z;
    public static KeyCode SKILL2_KEY = KeyCode.X;
    public static KeyCode SKILL3_KEY = KeyCode.C;

    public SettingManager(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
    }

    public void changeBackGroundMusic(){
        soundEngine.changeMusic();
    }

    public void changeFXVolume(double volume){
        soundEngine.changeFXVolume(volume);
    }

    public void changeMusicVolume(double volume){ soundEngine.changeMusicVolume(volume); }

    public double getFXVolume() { return soundEngine.getFXVolume(); }

    public double getMusicVolume() { return soundEngine.getMusicVolume(); }

    public void muteUnMuteMusic(){ soundEngine.pauseUnPauseMusic(); }

    /*
    public void changeControls() {
        KeyCode k = null;
        KeyCode newK = null;
        while (k == null){ k = InputManager.getPressedKey();}
        while (!InputManager.isKeyReleased()){
            try
                {Thread.sleep(0);}
            catch (Exception e)
                {e.printStackTrace();}
        }
        while (InputManager.isKeyReleased()){
            try
                {Thread.sleep(0);}
            catch (Exception e)
                {e.printStackTrace();}
        }
        while (newK == null){newK = InputManager.getPressedKey();}
        if (k == this.UP_KEY) this.UP_KEY = newK;
        else if (k == this.DOWN_KEY) changeDownKey(newK);
        else if (k == this.LEFT_KEY) changeLeftKey(newK);
        else if (k == this.RIGHT_KEY) changeRightKey(newK);
        else if (k == this.SHOOTING_KEY) changeShootKey(newK);
        else if (k == this.SKILL1_KEY) changeSkill1Key(newK);
        else if (k == this.SKILL2_KEY) changeSkill2Key(newK);
        else if (k == this.SKILL3_KEY) changeSkill3Key(newK);
    }
    */

    public void changeUpKey(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.UP_KEY = k;
    }

    public void changeDownKey(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.DOWN_KEY = k;
    }

    public void changeLeftKey(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.LEFT_KEY = k;
    }

    public void changeRightKey(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.RIGHT_KEY = k;
    }

    public void changeShootKey(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.SHOOTING_KEY = k;
    }

    public void changeSkill1Key(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.SKILL1_KEY = k;
    }

    public void changeSkill2Key(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.SKILL2_KEY = k;
    }

    public void changeSkill3Key(){
        KeyCode k = null;
        while (k == null){ k = InputManager.getPressedKey();}

        this.SKILL3_KEY = k;
    }

}