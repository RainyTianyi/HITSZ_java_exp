package edu.hitsz.action;

import edu.hitsz.DAO.DaoImpl;
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
        this.bossSpawnTriggerScore = 400;  // Boss出现所需分数较低
    }
}
