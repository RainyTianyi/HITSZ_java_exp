package edu.hitsz.item;

public class BloodItem extends BaseItem{

    public BloodItem(int locationX, int locationY, int speedX, int speedY, int value) {
        super(locationX, locationY, speedX, speedY, 0, value);
    }

    @Override
    public void activate() {}
}
