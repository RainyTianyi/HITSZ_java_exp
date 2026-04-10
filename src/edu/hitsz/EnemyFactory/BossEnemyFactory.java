package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft() {
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) + ImageManager.BOSS_ENEMY_IMAGE.getWidth() / 2;
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.15);
        int speedX = (int) (Math.random() * 6 - 3);
        int speedY = 0;
        int hp = 100;
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
