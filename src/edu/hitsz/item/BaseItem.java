package edu.hitsz.item;

import edu.hitsz.basic.AbstractFlyingObject;

public abstract class BaseItem extends AbstractFlyingObject {

    // 道具生效时间
    protected int duration;

    // 道具值
    protected int value;

    public BaseItem(int locationX, int locationY, int speedX, int speedY, int duration, int value) {
        super(locationX, locationY, speedX, speedY);
        this.duration = duration;
        this.value = value;
    }

    // 生效
    public abstract void activate();

    public int getDuration() {
        return duration;
    }

    public int getValue() {
        return value;
    }
}
