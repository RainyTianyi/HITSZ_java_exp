package edu.hitsz.action;

import edu.hitsz.DAO.PlayerScore;
import edu.hitsz.EnemyFactory.*;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.BombItem;
import edu.hitsz.item.IceItem;
import edu.hitsz.music.MusicController;
import edu.hitsz.music.MusicType;
import edu.hitsz.observer.Observer;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public abstract class AbstractAction {
    //音频管理
    private final MusicController musicController = new MusicController();

    //屏幕中出现的敌机最大数量
    protected int enemyMaxNumber;

    //敌机生成周期
    protected double enemySpawnCycle;
    protected int enemySpawnCounter;

    //Boss敌机生成，记录分数
    protected int bossSpawnTriggerScore;
    protected int bossSpawnScore;

    //英雄机和敌机射击周期
    protected double shootCycle;
    protected int shootCounter;

    /*
    public void action() {

        musicController.playBgm();
        musicController.enableBgmLoop(true);

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                enemySpawnCounter++;
                if (enemySpawnCounter >= enemySpawnCycle) {
                    enemySpawnCounter = 0;
                    EnemySpawnAction();
                }

                // 积分每到达阈值且当前不存在Boss敌机时，生成Boss敌机
                BossSpawnAction();
                // 检查Boss敌机是否死亡
                checkBossDeath();
                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 道具移动
                itemsMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 更新观察者
                updateObservers();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);
    }
    */

    //***********************
    //      Action 各部分
    //***********************


}
