package edu.hitsz.item;

import edu.hitsz.ShootStrategy.CircleShootStrategy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;

public class FirePlusItem extends BaseItem{

    public FirePlusItem(int locationX, int locationY, int speedX, int speedY, int duration) {
        super(locationX, locationY, speedX, speedY, duration, 0);
    }

    @Override
    public void activate() {
        HeroAircraft.getHeroAircraft().shootPattern.setShootStrategy(new CircleShootStrategy());
        System.out.println("FirePlusItem active!");
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
