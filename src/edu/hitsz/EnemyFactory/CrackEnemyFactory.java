package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.CrackEnemy;
import edu.hitsz.aircraft.EnemyAircraft;

public class CrackEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new CrackEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
