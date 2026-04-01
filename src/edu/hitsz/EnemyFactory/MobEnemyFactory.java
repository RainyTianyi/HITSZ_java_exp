package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;

public class MobEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
