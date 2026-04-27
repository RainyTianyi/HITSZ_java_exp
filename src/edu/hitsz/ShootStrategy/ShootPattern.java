package edu.hitsz.ShootStrategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class ShootPattern {

    public ShootStrategy shootStrategy;
    private ShootStrategy originalStrategy;
    private Thread currentRestoreThread;

    public ShootPattern(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
        this.originalStrategy = shootStrategy;
        this.currentRestoreThread = null;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public void setTemporaryStrategy(ShootStrategy temporaryStrategy, Thread restoreThread) {
        if (originalStrategy == null) {
            originalStrategy = this.shootStrategy;
        }

        if (currentRestoreThread != null && currentRestoreThread.isAlive()) {
            currentRestoreThread.interrupt();
        }

        this.shootStrategy = temporaryStrategy;
        this.currentRestoreThread = restoreThread;
    }

    public void restoreOriginalStrategy() {
        if (originalStrategy != null) {
            this.shootStrategy = originalStrategy;
            originalStrategy = null;
            currentRestoreThread = null;
        }
    }

    public List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY) {
        return shootStrategy.shoot(x, y, direction, power, aircraftSpeedY);
    }
}
