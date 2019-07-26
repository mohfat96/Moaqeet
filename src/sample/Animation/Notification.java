package sample.Animation;

import com.sun.istack.internal.NotNull;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import sample.ImagesManager.Background;
import sample.LoggingManager.Logger;
import sample.TimeManager.Counter;
import sample.TimeManager.Prayer;


public class Notification {


    enum Status {
        Ready, Playing
    }

    private Flicker iqamaFlicker = new Flicker();
    private Flicker prayerFlicker =  new Flicker();
    private Timeline iqamaTime;
    private Status status;
    private ImageView imageView;
    private Prayer prayer;
    private Label iqamaTimeLabel;
    private Image mainImage;
    private Counter counter = new Counter();


    public Notification(ImageView imageView , Background main) {
        this.imageView = imageView;
        mainImage = main.getImage();
    }


    public void setPrayer(@NotNull Prayer prayer, boolean sound) {
        if (prayer != null) {
            this.prayer = prayer;
            status = Status.Ready;
            iqamaTimeLabel = prayer.getIqamaLabel();
            prayerFlicker.setPrayer(prayer.getPrayerLabel() , sound);
            iqamaFlicker.setPrayer(iqamaTimeLabel , sound);
        }

    }

    public void play() {
        counter.reset();
        prayerFlicker.play();
        if(iqamaTime != null){
            iqamaTime.stop();
            iqamaTime = null;
        }

        if (status == Status.Ready) {
            System.out.println("playing notification");
            imageView.setImage(prayer.getPrayerImage());
            int time = prayer.getIqamaTime();
            iqamaTimeLabel.setText("" + time);
            iqamaTimeLabel.setVisible(true);
            iqamaTime = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
                int rest = time - counter.increment();
                if (rest < 0) {
                    iqamaTimeLabel.setVisible(false);
                    imageView.setImage(mainImage);
                    System.out.println("hide");
                } else {
                    if (rest == 0) {
                        iqamaTimeLabel.setText("" + rest);
                        iqamaFlicker.play();
                        System.out.println("Flicker set!");
                    } else {
                        iqamaFlicker.stop();
                        System.out.println("iqama time set!");
                        iqamaTimeLabel.setText("" + rest);
                    }
                }
            }));
            iqamaTime.setCycleCount(time + 1);
            iqamaTime.play();

        } else {
            System.out.println("notification is not ready set a new prayer!");
            Logger.info(Notification.class.getName() , "play" , "notification is not ready set a new prayer!" );
        }

    }


}
