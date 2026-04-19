package edu.hitsz.ShootStrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class SingleShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY) {
        List<BaseBullet> res = new LinkedList<>();
        BaseBullet bullet;
        int speedX = 0;
        int speedY = aircraftSpeedY + 5 * direction;
        if (direction == -1) {
            // 代表是英雄机
            bullet = new HeroBullet(x, y, speedX, speedY, power);
        }
        else {
            // 代表是敌机
            bullet = new EnemyBullet(x, y, speedX, speedY, power);
        }
        res.add(bullet);
        return res;
    }
}
