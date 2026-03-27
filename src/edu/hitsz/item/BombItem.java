package edu.hitsz.item;

public class BombItem extends BaseItem{
    public BombItem(int locationX, int locationY, int speedX, int speedY, int value) {
        super(locationX, locationY, speedX, speedY, 0, value);
    }

    @Override
    public void activate() {}
}
