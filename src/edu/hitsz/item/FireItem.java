package edu.hitsz.item;

import edu.hitsz.ShootStrategy.ScatterShootStrategy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;

public class FireItem extends BaseItem{

    public FireItem(int locationX, int locationY, int speedX, int speedY, int duration) {
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
                    System.out.println("FireItem effect expired, restored original strategy!");
                }
            } catch (InterruptedException e) {
                System.out.println("FireItem effect interrupted by new item");
            }
        });
        restoreThread.setDaemon(true);

        hero.shootPattern.setTemporaryStrategy(new ScatterShootStrategy(), restoreThread);
        restoreThread.start();

        System.out.println("FireItem active! Duration: " + duration + "ms");
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
