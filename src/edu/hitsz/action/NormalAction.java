package edu.hitsz.action;

import edu.hitsz.DAO.DaoImpl;
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
        this.bossSpawnTriggerScore = 500;  // Boss出现所需分数适中
    }
}
