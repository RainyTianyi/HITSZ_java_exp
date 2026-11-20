package edu.hitsz.android.game.enemyfactory;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.aircraft.CrackEnemy;
import edu.hitsz.android.game.aircraft.EnemyAircraft;
import edu.hitsz.android.game.Arena;

public class CrackEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(double difficultyFactor) {
        int width = Sprite.CRACK.width;
        int locationX = width / 2 + (int) (Math.random() * (Arena.WIDTH - width));
        int locationY = (int) (Math.random() * Arena.HEIGHT * 0.2);
        // 横向速度也随难度略微增加
        int speedX = (int) ((Math.random() * 10 - 5) * difficultyFactor);
        // 纵向速度随难度增加
        int speedY = (int) (8 * difficultyFactor);
        // 血量随难度增加
        int hp = (int) (50 * difficultyFactor);
        return new CrackEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
