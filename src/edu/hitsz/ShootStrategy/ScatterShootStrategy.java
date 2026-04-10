package edu.hitsz.ShootStrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShootStrategy implements ShootStrategy{

    // 默认每次发射三枚子弹
    private static final int shootNum = 3;

    // 扇形半顶角
    private static final int angle = 30;

    // 子弹速度（相对飞机）
    private static final int speed = 10;

    @Override
    public List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY) {
        List<BaseBullet> res = new LinkedList<>();
        y = y + direction * 2;
        BaseBullet bullet;

        // 生成三个子弹的角度
        int[] angles = new int[]{-angle, 0, angle};
        // 子弹速度矢量为speed，角度为-angel, 0, angel
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int speedX = direction * (int) (speed * Math.sin(Math.toRadians(angles[i])));
            int speedY = direction * ((int) (speed * Math.cos(Math.toRadians(angles[i]))) + aircraftSpeedY);
            if (direction == -1) {
                // 代表是英雄机
                bullet = new HeroBullet(x + -(i*2 - shootNum + 1)*20, y, speedX, speedY, power);
            }
            else {
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 20, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
