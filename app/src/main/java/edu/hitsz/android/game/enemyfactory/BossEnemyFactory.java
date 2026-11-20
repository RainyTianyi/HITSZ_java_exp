package edu.hitsz.android.game.enemyfactory;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.aircraft.BossEnemy;
import edu.hitsz.android.game.aircraft.EnemyAircraft;
import edu.hitsz.android.game.Arena;

public class BossEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(double difficultyFactor) {
        int width = Sprite.BOSS.width;
        int locationX = width / 2 + (int) (Math.random() * (Arena.WIDTH - width));
        int locationY = Sprite.BOSS.height / 2;
        // 横向速度随难度略微增加
        int speedX = (int) ((Math.random() * 6 - 3) * difficultyFactor);
        int speedY = 0;
        // Boss血量随难度大幅增加
        int hp = (int) (200 * difficultyFactor);
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
