package sample.TimeManager;

public class Counter {
    int limit = 0;
    int counter = 0;

    public Counter() {
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int increment(){
        counter = counter + 1;
        if (limit != 0 && counter >= limit){
            counter = 0;
        }
        return counter;
    }

    public int getCounter() {
        return counter;
    }

    public void reset(){
        counter = 0;
    }
}
