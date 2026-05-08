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
 * 普通难度
 */
public class NormalAction extends AbstractAction {

    public NormalAction(String difficulty, HeroAircraft heroAircraft,
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
        // 普通难度参数设置（保持原有游戏逻辑）
        this.enemyMaxNumber = 5;           // 敌机最大数量适中
        this.enemySpawnCycle = 20;         // 敌机生成周期适中
        this.heroShootCycle = 20;          // 英雄机射击周期适中
        this.enemyShootCycle = 20;         // 敌机射击周期适中
        this.bossSpawnTriggerScore = 600;  // Boss出现所需分数适中
    }

    @Override
    protected void EnemySpawnAction() {
        EnemyAircraftFactory enemyAircraftFactory;
        // 普通难度：均衡的敌机生成概率
        int type = (int) (Math.random() * 100);
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (type < 40) {
                // 40% 概率生成普通敌机
                enemyAircraftFactory = new MobEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
            }
            else if (type < 70){
                // 30% 概率生成精英敌机
                enemyAircraftFactory = new EliteEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
            }
            else if (type < 90){
                // 20% 概率生成Crack敌机
                enemyAircraftFactory = new CrackEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
            }
            else{
                // 10% 概率生成Ace敌机
                enemyAircraftFactory = new AceEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
            }
        }
    }
}
