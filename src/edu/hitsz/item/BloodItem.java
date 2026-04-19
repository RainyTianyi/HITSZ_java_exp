package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;

public class BloodItem extends BaseItem{

    public BloodItem(int locationX, int locationY, int speedX, int speedY, int value) {
        super(locationX, locationY, speedX, speedY, 0, value);
    }

    @Override
    public void activate() {
        HeroAircraft.getHeroAircraft().increaseHp(value);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
