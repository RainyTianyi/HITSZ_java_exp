package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft;

public interface EnemyAircraftFactory {
    EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp);
}
