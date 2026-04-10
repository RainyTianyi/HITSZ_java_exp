package edu.hitsz.ShootStrategy;

import edu.hitsz.bullet.BaseBullet;
import java.util.List;

public interface ShootStrategy {
    List<BaseBullet> shoot(int x, int y, int direction, int power, int aircraftSpeedY);
}
