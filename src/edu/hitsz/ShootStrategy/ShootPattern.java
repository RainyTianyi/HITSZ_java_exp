package edu.hitsz.ShootStrategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class ShootPattern {
    // 维护一个策略对象
    public ShootStrategy shootStrategy;

    // 构造函数
    public ShootPattern(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    // 设置发射策略
    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    // 调用发射策略
    public List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY) {
        return shootStrategy.shoot(x, y, direction, power, aircraftSpeedY);
    }
}
