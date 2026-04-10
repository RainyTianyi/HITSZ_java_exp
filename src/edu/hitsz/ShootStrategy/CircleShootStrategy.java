package edu.hitsz.ShootStrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class CircleShootStrategy implements ShootStrategy{
    @Override
    public List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY) {
        List<BaseBullet> res = new LinkedList<>();
        y = y + direction * 2;
        BaseBullet bullet;

        // 生成子弹的角度组，360度均分20个
        int shootNum = 20;
        int speed = 10;
        int[] angles = new int[shootNum];
        for(int i=0; i<shootNum; i++){
            angles[i] = i * 360 / shootNum;
        }

        for(int i=0; i<shootNum; i++){
            // 根据角度生成子弹的速度
            int speedX = (int) (speed * Math.sin(Math.toRadians(angles[i])));
            int speedY = (int) (speed * Math.cos(Math.toRadians(angles[i]))) + aircraftSpeedY;
            if (direction == -1) {
                // 代表是英雄机 子弹数量减半
                if (i % 2 == 0) {
                    continue;
                }
                bullet = new HeroBullet(x, y, speedX, speedY, power);
            }
            else {

                bullet = new EnemyBullet(x, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
