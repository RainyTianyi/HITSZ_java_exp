package edu.hitsz.action;

import edu.hitsz.DAO.DaoImpl;
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
        this.enemyMaxNumber = 3;           // 敌机最大数量较少
        this.enemySpawnCycle = 30;         // 敌机生成周期较长（生成较慢）
        this.shootCycle = 25;              // 射击周期较长（射击频率较低）
        this.bossSpawnTriggerScore = 600;  // Boss出现所需分数较高
    }
}
