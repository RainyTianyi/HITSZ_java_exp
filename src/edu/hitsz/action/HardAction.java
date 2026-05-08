package edu.hitsz.action;

import edu.hitsz.DAO.DaoImpl;
import edu.hitsz.EnemyFactory.*;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.aircraft.EnemyAircraft;

import javax.swing.*;
import java.util.List;

/**
 * 困难难度
 */
public class HardAction extends AbstractAction {

    public HardAction(String difficulty, HeroAircraft heroAircraft,
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
        // 困难难度参数设置
        this.enemyMaxNumber = 7;           // 敌机最大数量较多
        this.enemySpawnCycle = 15;         // 敌机生成周期较短（生成较快）
        this.heroShootCycle = 20;          // 英雄机射击周期适中
        this.enemyShootCycle = 12;         // 敌机射击周期较短（射击频率较高）
        this.bossSpawnTriggerScore = 500;  // Boss出现所需分数较低
    }

    @Override
    protected void EnemySpawnAction() {
        EnemyAircraftFactory enemyAircraftFactory;
        // 困难难度：更少普通敌机，更多精英敌机
        int type = (int) (Math.random() * 100);
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (type < 25) {
                // 25% 概率生成普通敌机
                enemyAircraftFactory = new MobEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
            else if (type < 60){
                // 35% 概率生成精英敌机
                enemyAircraftFactory = new EliteEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
            else if (type < 85){
                // 25% 概率生成Crack敌机
                enemyAircraftFactory = new CrackEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
            else{
                // 15% 概率生成Ace敌机
                enemyAircraftFactory = new AceEnemyFactory();
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
            }
        }
    }

    @Override
    protected void BossSpawnAction() {
        if (bossSpawnScore >= bossSpawnTriggerScore) {
            boolean hasBoss = false;
            for (EnemyAircraft enemy : enemyAircrafts) {
                if (enemy instanceof BossEnemy) {
                    hasBoss = true;
                    break;
                }
            }
            if (!hasBoss) {
                EnemyAircraftFactory enemyAircraftFactory = new BossEnemyFactory();
                // 困难难度Boss血量随难度增加，使用当前难度系数
                enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft(difficultyFactor));
                bossSpawnScore = 0;
                // 记录Boss生成次数并更新难度
                bossSpawnCount++;
                updateDifficultyFactor();
                // 困难难度下更新射击周期
                updateShootCycles();
                System.out.println("Boss spawned! Current difficulty factor: " + difficultyFactor + ", bossSpawnCount: " + bossSpawnCount);
                musicController.playBossBgm();
                musicController.enableBgmLoop(true);
            }
        }
    }

    @Override
    protected void updateShootCycles() {
        // 困难难度：射击周期随难度系数减小（频率增加）
        // 英雄机射击周期：基准20，最小10
        this.heroShootCycle = Math.max(10, 20 / difficultyFactor);
        // 敌机射击周期：基准12，最小5
        this.enemyShootCycle = Math.max(5, 12 / difficultyFactor);
    }
}
