package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EnemyAircraft;

public class EliteEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
