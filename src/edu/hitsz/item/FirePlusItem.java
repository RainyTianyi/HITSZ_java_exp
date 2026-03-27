package edu.hitsz.item;

public class FirePlusItem extends BaseItem{

    public FirePlusItem(int locationX, int locationY, int speedX, int speedY, int duration) {
        super(locationX, locationY, speedX, speedY, duration, 0);
    }

    @Override
    public void activate() {}
}
