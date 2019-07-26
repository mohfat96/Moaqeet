package sample.Animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

public class Flicker {

    enum Status {
        playing , stopped
    }

    private  Timeline flicker = new Timeline();
    private  Label prayer = null;

    Media media ;
    MediaPlayer mediaPlayer ;
    boolean sound = true;

    Status status;

    public Flicker() {
        media =  new Media(Paths.get("src/sample/resources/Sounds/Tone.mp3").toUri().toString());
         mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(2);
            mediaPlayer.setVolume(1);
    }

    public void setPrayer(Label prayer , boolean sound){
            this.sound = sound;
            this.prayer = prayer;
            mediaPlayer.stop();
            flicker.stop();
            status = Status.stopped;
            flicker.getKeyFrames().clear();
            flicker.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(prayer.opacityProperty(), 0)));
            flicker.setCycleCount(30);
            flicker.setAutoReverse(true);

    }

    public void play(){
        if (status != Status.playing){
            System.out.println("flicker is playing");
        status = Status.playing;
        flicker.play();
        if(sound){
            mediaPlayer.play();
        }
        }
    }

    public void stop(){
        if (status != Status.stopped){
        status = Status.stopped;
        flicker.stop();
        if (prayer != null) {
            prayer.setOpacity(1);
        }
        }
    }

}
