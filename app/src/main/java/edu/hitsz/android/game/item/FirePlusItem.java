package edu.hitsz.android.game.item;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.shootstrategy.CircleShootStrategy;
import edu.hitsz.android.game.aircraft.HeroAircraft;

public class FirePlusItem extends BaseItem{

    public FirePlusItem(int locationX, int locationY, int speedX, int speedY, int duration) {
        super(Sprite.FIRE_PLUS, locationX, locationY, speedX, speedY, duration, 0);
    }

    @Override
    public void activate(HeroAircraft hero) {
        hero.powerUp(new CircleShootStrategy(), duration);
    }

}
