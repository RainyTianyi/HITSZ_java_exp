package edu.hitsz.android.game.item;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.shootstrategy.ScatterShootStrategy;
import edu.hitsz.android.game.aircraft.HeroAircraft;

public class FireItem extends BaseItem{

    public FireItem(int locationX, int locationY, int speedX, int speedY, int duration) {
        super(Sprite.FIRE, locationX, locationY, speedX, speedY, duration, 0);
    }

    @Override
    public void activate(HeroAircraft hero) {
        hero.powerUp(new ScatterShootStrategy(), duration);
    }

}
