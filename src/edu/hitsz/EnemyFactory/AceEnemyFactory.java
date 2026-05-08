package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class AceEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(double difficultyFactor) {
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ACE_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2);
        // 横向速度也随难度略微增加
        int speedX = (int) ((Math.random() * 10 - 5) * difficultyFactor);
        // 纵向速度随难度增加
        int speedY = (int) (6 * difficultyFactor);
        // 血量随难度增加
        int hp = (int) (60 * difficultyFactor);
        return new AceEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
