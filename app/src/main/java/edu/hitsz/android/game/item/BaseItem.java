package edu.hitsz.android.game.item;

import edu.hitsz.android.game.Arena;
import edu.hitsz.android.game.Sprite;
import edu.hitsz.android.game.aircraft.HeroAircraft;

import edu.hitsz.android.game.basic.AbstractFlyingObject;

public abstract class BaseItem extends AbstractFlyingObject {

    // 道具生效时间
    protected int duration;

    // 道具值
    protected int value;

    public BaseItem(Sprite sprite, int locationX, int locationY, int speedX, int speedY, int duration, int value) {
        super(sprite, locationX, locationY, speedX, speedY);
        this.duration = duration;
        this.value = value;
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Arena.HEIGHT) {
            vanish();
        }
    }

    public abstract void activate(HeroAircraft hero);

    public int getDuration() {
        return duration;
    }

    public int getValue() {
        return value;
    }
}
