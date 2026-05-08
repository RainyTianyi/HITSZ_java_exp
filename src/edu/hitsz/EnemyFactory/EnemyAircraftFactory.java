package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft;

public interface EnemyAircraftFactory {
    /**
     * 创建敌机
     * @param difficultyFactor 难度系数（1.0为基准，越大越难）
     * @return 创建的敌机对象
     */
    EnemyAircraft createEnemyAircraft(double difficultyFactor);
}
