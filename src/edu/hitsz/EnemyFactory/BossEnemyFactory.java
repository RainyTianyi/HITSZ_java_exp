package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EnemyAircraft;

public class BossEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
