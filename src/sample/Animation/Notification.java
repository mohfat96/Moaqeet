package sample.Animation;

import com.sun.istack.internal.NotNull;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.ImagesManager.Background;
import sample.TimeManager.Counter;
import sample.TimeManager.Prayer;


public class Notification {


    enum Status {
        Ready, Playing
    }

    private Flicker flicker = new Flicker();
    private Flicker flicker2 =  new Flicker();
    Timeline iqamaTime;
    Status status;
    Timeline show;
    Timeline hide;
    AnchorPane anchorPane;
    private ImageView imageView;
    private  Background main;
    Prayer prayer;
    Label iqamaTimeLabel;
    Image mainImage;
    Counter counter = new Counter();


    public Notification(ImageView imageView , Background main) {
        this.imageView = imageView;
        mainImage = main.getImage();
    }


    public void setPrayer(@NotNull Prayer prayer) {
        if (prayer != null) {
            this.prayer = prayer;
            status = Status.Ready;
            iqamaTimeLabel = prayer.getIqamaLabel();
            flicker2.setPrayer(prayer.getPrayerLabel());
            flicker.setPrayer(iqamaTimeLabel);
        }

    }

    public void play() {
        counter.reset();
        flicker2.play();
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
                        flicker.play();
                        System.out.println("Flicker set!");
                    } else {
                       flicker.stop();
                        System.out.println("iqama time set!");
                        iqamaTimeLabel.setText("" + rest);
                    }
                }
            }));
            iqamaTime.setCycleCount(time + 1);
            iqamaTime.play();

        } else
            System.out.println("notification is not ready set a new prayer!");
    }


}
