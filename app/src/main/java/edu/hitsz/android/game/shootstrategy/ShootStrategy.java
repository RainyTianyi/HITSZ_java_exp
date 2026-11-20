package edu.hitsz.android.game.shootstrategy;

import edu.hitsz.android.game.bullet.BaseBullet;
import java.util.List;

public interface ShootStrategy {
    List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY);
}
