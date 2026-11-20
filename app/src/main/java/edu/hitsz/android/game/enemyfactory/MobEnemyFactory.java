package edu.hitsz.android.game.enemyfactory;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.aircraft.EnemyAircraft;
import edu.hitsz.android.game.aircraft.MobEnemy;
import edu.hitsz.android.game.Arena;

public class MobEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(double difficultyFactor) {
        int width = Sprite.MOB.width;
        int locationX = width / 2 + (int) (Math.random() * (Arena.WIDTH - width));
        int locationY = (int) (Math.random() * Arena.HEIGHT * 0.2);
        int speedX = 0;
        // 速度随难度增加
        int speedY = (int) (10 * difficultyFactor);
        // 血量随难度增加
        int hp = (int) (10 * difficultyFactor);
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
