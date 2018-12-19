import com.sun.istack.internal.Nullable;
import javafx.scene.media.AudioClip;

import java.net.URL;

public class BackGroundMusic {
    private static URL musicURL;
    private static AudioClip audioClip;
    private static AudioClip remixAudioClip;

    public static void stop(){
        if(audioClip != null){
            audioClip.stop();
            audioClip = null;
        }
        if(remixAudioClip != null) {
            remixAudioClip.stop();
            remixAudioClip = null;
        }
    }

    public static void play(@Nullable final String musicName){
        if(musicName == null){
            musicURL = new BackGroundMusic().getClass().getResource("/music/huluwa.mp3");
            audioClip = new AudioClip(musicURL.toExternalForm());
            audioClip.play();
        }else{
            if(musicName.equals("huluwa")) {
                stop();
                musicURL = new BackGroundMusic().getClass().getResource("/music/huluwa.mp3");
                audioClip = new AudioClip(musicURL.toExternalForm());
                audioClip.play();
            }
            if(musicName.equals("dazhangweihuluwa")) {
                stop();
                musicURL = new BackGroundMusic().getClass().getResource("/music/dazhangweihuluwa.mp3");
                audioClip = new AudioClip(musicURL.toExternalForm());
                audioClip.play();
            }
            if(musicName.equals("remix")){
                stop();
                musicURL = new BackGroundMusic().getClass().getResource("/music/huluwa.mp3");
                audioClip = new AudioClip(musicURL.toExternalForm());
                audioClip.play();
                musicURL = new BackGroundMusic().getClass().getResource("/music/dazhangweihuluwa.mp3");
                remixAudioClip = new AudioClip(musicURL.toExternalForm());
                remixAudioClip.play();
            }
            if(musicName.equals("stop")){
                stop();
            }
        }
    }
}
