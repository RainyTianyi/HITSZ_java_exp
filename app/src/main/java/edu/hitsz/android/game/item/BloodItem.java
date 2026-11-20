package edu.hitsz.android.game.item;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.aircraft.HeroAircraft;

public class BloodItem extends BaseItem{

    public BloodItem(int locationX, int locationY, int speedX, int speedY, int value) {
        super(Sprite.BLOOD, locationX, locationY, speedX, speedY, 0, value);
    }

    @Override
    public void activate(HeroAircraft hero) {
        hero.increaseHp(value);
    }

}
