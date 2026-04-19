package edu.hitsz.ShootStrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class DoubleShootStrategy implements ShootStrategy{
    @Override
    public List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY) {
        List<BaseBullet> res = new LinkedList<>();
        BaseBullet bullet;
        int speedX = 0;
        int speedY = aircraftSpeedY + 5 * direction;
        int shootNum = 2;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*20, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
