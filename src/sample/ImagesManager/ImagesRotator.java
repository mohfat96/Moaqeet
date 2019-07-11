package sample.ImagesManager;

import java.util.ArrayList;
import java.util.Iterator;

public class ImagesRotator {
    ArrayList<Background> images;
    Iterator<Background> imageIterator;



    public ImagesRotator(ArrayList<Background> images) {
        this.images = images;
        imageIterator = images.iterator();
    }

    public Background next(){
        if(imageIterator.hasNext()){
            return imageIterator.next();
        }
        else {
            imageIterator = images.iterator();
            return imageIterator.next();
        }
    }

    public void reset(){
        imageIterator = images.iterator();
    }

    public int getSize(){
        return images.size();
    }
}
