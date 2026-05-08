package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(double difficultyFactor) {
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) + ImageManager.BOSS_ENEMY_IMAGE.getWidth() / 2;
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.15);
        // 横向速度随难度略微增加
        int speedX = (int) ((Math.random() * 6 - 3) * difficultyFactor);
        int speedY = 0;
        // Boss血量随难度大幅增加
        int hp = (int) (200 * difficultyFactor);
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
