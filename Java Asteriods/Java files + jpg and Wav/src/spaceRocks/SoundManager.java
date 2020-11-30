package spaceRocks;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * This class is used to manage the sounds that play when different events happen during the game
 * I used AudioClip as it seems to be the simplest way to do basic sounds
 * as it handles all the line/mixer stuff for you.
 *
 * The sounds used are taken from windows and are not my own
 *
 */

public class SoundManager
{
    private Map<String,AudioClip> soundCollection=new HashMap<String,AudioClip>();
    private boolean silent;
    
    public SoundManager()
    {
        for(String name : Arrays.asList("load","start","end","create","death","shoot","split","gone","shieldson","shieldsoff","teleport"))
        {
            try {
                soundCollection.put(name, Applet.newAudioClip(getClass().getResource("resources/" + name +".wav")));
            } catch (Exception ex) {
            }
        }
        silent=soundCollection.size()==0;
    }
 
    public void play(String name) 
    {
        AudioClip ac=soundCollection.get(name);
        if (ac!=null)
            try { 
                if (silent)
                    ac.stop();
                else
                    ac.play(); 
            } catch (Exception ex) {}
    }
    
    public void stop(String name) 
    {
        AudioClip ac=soundCollection.get(name);
        if (ac!=null)
            try { 
                ac.stop(); 
            } catch (Exception ex) {}
    }
    
    public void mute(boolean b) 
    {
        silent=(soundCollection.size()>0) ? b : false;
    }
    
    public boolean isSilent() 
    {
        return silent;
    }
    
    public void toggleSound() 
    {
        mute(!silent);
    }
    
    
}