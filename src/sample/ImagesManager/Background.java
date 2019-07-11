package sample.ImagesManager;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import sample.TimeManager.Prayer;

import java.util.ArrayList;

public class Background {

    Image image;
    BackgroundType type;
    ArrayList<Label> labels = new ArrayList<>();

    public Background(Image image, BackgroundType type) {
        this.image = image;
        this.type = type;
    }

    public Background(Image image, BackgroundType type, ArrayList<Prayer> prayers) {
        this.image = image;
        this.type = type;
        prayers.forEach(prayer -> {labels.add(prayer.getPrayerLabel());});
    }

    public Background(Image image , BackgroundType type , Label label){
        this.image = image;
        this.type = type;
        this.labels.add(label);
    }

    public Image getImage() {
        return image;
    }

    public BackgroundType getType() {
        return type;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void showLabels(){
        for (Label l:
             labels) {
            l.setVisible(true);
        }
    }

    public void hideLabels(){
        for (Label l:
             labels) {
            l.setVisible(false);
        }
    }
}
