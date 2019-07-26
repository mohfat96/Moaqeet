package sample.TimeManager;


import com.sun.istack.internal.NotNull;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.time.Duration;
import java.time.LocalTime;


public class Prayer {
    private Label prayerLabel;
    private Image prayerImage;
    private Label iqamaLabel;
    private LocalTime prayerTime;
    private int hour;
    private int minute;
    private int iqamaTime;
    private PrayerType prayerType;
    boolean isSet = false;

    public Prayer(){

    }

    public Label getIqamaLabel() {
        return iqamaLabel;
    }

    public Prayer(PrayerType prayerType, @NotNull Label prayerLabel, Label iqamaLabel, Image prayerImage) {
        this.prayerType = prayerType;
        this.prayerLabel = prayerLabel;
        this.prayerImage = prayerImage;
        this.iqamaLabel = iqamaLabel;
    }

//    public Prayer( PrayerType prayerType,Label prayerLabel, Image prayerImage, LocalTime prayerTime) {
//        this.prayerType = prayerType;
//        this.prayerLabel = prayerLabel;
//        this.prayerImage = prayerImage;
//        this.prayerTime = prayerTime;
//        hour = prayerTime.getHour();
//        minute = prayerTime.getMinute();
//
//    }

    public PrayerType getPrayerType() {
        return prayerType;
    }

    public Label getPrayerLabel() {
        return prayerLabel;
    }

    public Image getPrayerImage() {
        return prayerImage;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setPrayerTime(LocalTime prayerTime , LocalTime iqamaTime) {
        this.prayerTime = prayerTime;
        this.hour = prayerTime.getHour();
        this.minute = prayerTime.getMinute();
        prayerLabel.setText(prayerTime.toString());
        this.iqamaTime = ((int) Duration.between(prayerTime, iqamaTime).toMinutes());
        isSet = true;

    }

    public int getHour() throws IllegalStateException{
        if (isSet == true)
        {return hour;}
        else {
            throw new IllegalStateException("Prayer Time is not set!");
        }
    }

    public int getMinute() throws IllegalStateException{
        if (isSet == true)
        {return minute;}
        else {
            throw new IllegalStateException("Prayer Time is not set!");
        }
    }


    public int getIqamaTime() throws IllegalStateException{
        if (isSet == true)
        {return iqamaTime;}
        else {
            throw new IllegalStateException("Prayer Time is not set!");
        }
    }
}


