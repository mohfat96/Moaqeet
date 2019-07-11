package sample.Animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import sample.ImagesManager.Background;
import sample.ImagesManager.BackgroundType;
import sample.ImagesManager.ImagesRotator;
import sample.TimeManager.Counter;
import java.util.PriorityQueue;
import java.util.Queue;

public class SlideShow {

    Queue<Background> backgrounds = new PriorityQueue<>();
    Timeline timeline = new Timeline();
    ImageView imageView;
    ImagesRotator rotator;
    Counter counter = new Counter();
    boolean ready = false;
    int x;


    public SlideShow(ImageView imageView , ImagesRotator rotator){
        this.imageView = imageView;
        this.rotator = rotator;
        x = 60 / (rotator.getSize() +1);
    }


    public void Set(){
        ready = true;
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (counter.getCounter() == 0) {
                    if (backgrounds.size() != 0){ //hide label of previous Background
                        backgrounds.poll().hideLabels();
                    }
                    Background background = rotator.next();
                    backgrounds.offer(background);
                    if (background.getType() == BackgroundType.PrayerBackground) {
                        counter.setLimit(x * 2);
                    } else {
                        counter.setLimit(x);
                    }
                    imageView.setImage(background.getImage());
                    background.showLabels();
                }
                counter.increment();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void play(){
        if (ready) {
            timeline.play();
        } else {
            throw new IllegalStateException("Slideshow not ready!");
        }
    }

    public void pause(){
        if (ready) {
            if (backgrounds.size() > 0){
                backgrounds.poll().hideLabels();
            }
            timeline.stop();
            rotator.reset();
            Background background = rotator.next();
            backgrounds.offer(background);
            imageView.setImage(background.getImage());
            background.showLabels();
        }
        else {
            throw new IllegalStateException("Slideshow not ready!");
        }

    }

}
