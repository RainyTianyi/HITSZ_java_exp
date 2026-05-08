package edu.hitsz.action;

import edu.hitsz.DAO.DaoImpl;
import edu.hitsz.EnemyFactory.*;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.aircraft.EnemyAircraft;

import javax.swing.*;
import java.util.List;

/**
 * 简单难度
 */
public class SimpleAction extends AbstractAction {

    public SimpleAction(String difficulty, HeroAircraft heroAircraft,
                        List<EnemyAircraft> enemyAircrafts,
                        List<BaseBullet> heroBullets,
                        List<BaseBullet> enemyBullets,
                        List<BaseItem> items,
                        DaoImpl playerScores,
                        JPanel gamePanel) {
        super(difficulty, heroAircraft, enemyAircrafts, heroBullets, enemyBullets, items, playerScores, gamePanel);
    }

    @Override
    protected void initParameters() {
        // 简单难度参数设置
        this.enemyMaxNumber = 4;           // 敌机最大数量较少
        this.enemySpawnCycle = 25;         // 敌机生成周期较长（生成较慢）
        this.heroShootCycle = 20;          // 英雄机射击周期适中
        this.enemyShootCycle = 30;         // 敌机射击周期较长（射击频率较低）
        this.bossSpawnTriggerScore = 600;  // Boss出现所需分数
    }

    @Override
    protected void EnemySpawnAction() {
        EnemyAircraftFactory enemyAircraftFactory;
        // 简单难度：更多普通敌机，更少精英敌机
        int type = (int) (Math.random() * 100);
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (type < 60) {
                // 60% 概率生成普通敌机
                enemyAircraftFactory = new MobEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
            else if (type < 80){
                // 20% 概率生成精英敌机
                enemyAircraftFactory = new EliteEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
            else if (type < 95){
                // 15% 概率生成Crack敌机
                enemyAircraftFactory = new CrackEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
            else{
                // 5% 概率生成Ace敌机
                enemyAircraftFactory = new AceEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
        }
    }

    @Override
    protected void BossSpawnAction() {
        // 简单难度：不生成Boss敌机
        // 此方法为空实现，简单难度下永远不会生成Boss
    }

    @Override
    protected void updateShootCycles() {
        // 简单难度：不改变射击周期
        // 此方法为空实现
    }
}
