package edu.hitsz.item;

public class FireItem extends BaseItem{

    public FireItem(int locationX, int locationY, int speedX, int speedY, int duration) {
        super(locationX, locationY, speedX, speedY, duration, 0);
    }

    @Override
    public void activate() {}
}
