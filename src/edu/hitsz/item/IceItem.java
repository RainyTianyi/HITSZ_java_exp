package edu.hitsz.item;

public class IceItem extends BaseItem{

    public IceItem(int locationX, int locationY, int speedX, int speedY, int duration) {
        super(locationX, locationY, speedX, speedY, duration, 0);
    }

    @Override
    public void activate() {}
}
