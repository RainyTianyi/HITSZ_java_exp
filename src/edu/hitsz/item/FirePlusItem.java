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
        HeroAircraft hero = HeroAircraft.getHeroAircraft();

        Thread restoreThread = new Thread(() -> {
            try {
                Thread.sleep(duration);
                if (!Thread.currentThread().isInterrupted()) {
                    hero.shootPattern.restoreOriginalStrategy();
                    System.out.println("FirePlusItem effect expired, restored original strategy!");
                }
            } catch (InterruptedException e) {
                System.out.println("FirePlusItem effect interrupted by new item");
            }
        });
        restoreThread.setDaemon(true);

        hero.shootPattern.setTemporaryStrategy(new CircleShootStrategy(), restoreThread);
        restoreThread.start();

        System.out.println("FirePlusItem active! Duration: " + duration + "ms");
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
