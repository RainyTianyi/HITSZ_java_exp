package edu.hitsz.android.game.enemyfactory;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.aircraft.EliteEnemy;
import edu.hitsz.android.game.aircraft.EnemyAircraft;
import edu.hitsz.android.game.Arena;

public class EliteEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(double difficultyFactor) {
        int width = Sprite.ELITE.width;
        int locationX = width / 2 + (int) (Math.random() * (Arena.WIDTH - width));
        int locationY = (int) (Math.random() * Arena.HEIGHT * 0.2);
        // 横向速度也随难度略微增加
        int speedX = (int) ((Math.random() * 10 - 5) * difficultyFactor);
        // 纵向速度随难度增加
        int speedY = (int) (10 * difficultyFactor);
        // 血量随难度增加
        int hp = (int) (30 * difficultyFactor);
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
