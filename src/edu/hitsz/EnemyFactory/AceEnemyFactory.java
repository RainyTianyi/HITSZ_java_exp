package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.EnemyAircraft;

public class AceEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new AceEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
