package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(double difficultyFactor) {
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2);
        int speedX = 0;
        // 速度随难度增加
        int speedY = (int) (10 * difficultyFactor);
        // 血量随难度增加
        int hp = (int) (10 * difficultyFactor);
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
