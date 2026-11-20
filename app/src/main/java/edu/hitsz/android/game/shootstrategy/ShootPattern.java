package edu.hitsz.android.game.shootstrategy;

import edu.hitsz.android.game.bullet.BaseBullet;

import java.util.List;

public class ShootPattern {

    private final ShootStrategy originalStrategy;
    private ShootStrategy shootStrategy;

    public ShootPattern(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
        originalStrategy = shootStrategy;
    }

    public void setTemporaryStrategy(ShootStrategy temporaryStrategy) {
        shootStrategy = temporaryStrategy;
    }

    public void restoreOriginalStrategy() {
        shootStrategy = originalStrategy;
    }

    public List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY) {
        return shootStrategy.shoot(x, y, direction, power, aircraftSpeedY);
    }
}
