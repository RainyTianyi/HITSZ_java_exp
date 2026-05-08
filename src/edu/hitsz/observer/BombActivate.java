package edu.hitsz.observer;

import edu.hitsz.aircraft.EnemyAircraft;

import java.util.ArrayList;
import java.util.List;

public class BombActivate extends ItemActivate{
    // 记录被炸弹击毁的敌机，用于后续加分
    private List<EnemyAircraft> bombedEnemies = new ArrayList<>();

    @Override
    public void notifyObservers() {
        bombedEnemies.clear();
        for (Observer observer : observers) {
            if (observer instanceof EnemyAircraft) {
                EnemyAircraft enemy = (EnemyAircraft) observer;
                if (!enemy.notValid()) {
                    // 记录被炸弹击毁的敌机
                    bombedEnemies.add(enemy);
                }
            }
            observer.beBombed();
        }
    }

    /**
     * 获取被炸弹击毁的敌机列表
     * @return 被炸弹击毁的敌机列表
     */
    public List<EnemyAircraft> getBombedEnemies() {
        return bombedEnemies;
    }
}
