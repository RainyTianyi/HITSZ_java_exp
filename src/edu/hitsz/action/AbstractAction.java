package edu.hitsz.action;

import edu.hitsz.DAO.DaoImpl;
import edu.hitsz.DAO.PlayerScore;
import edu.hitsz.EnemyFactory.*;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.BombItem;
import edu.hitsz.item.IceItem;
import edu.hitsz.music.MusicController;
import edu.hitsz.music.MusicType;
import edu.hitsz.observer.BombActivate;
import edu.hitsz.observer.IceActivate;
import edu.hitsz.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractAction {
    //音频管理
    protected MusicController musicController = new MusicController();

    //屏幕中出现的敌机最大数量
    protected int enemyMaxNumber;

    //敌机生成周期
    protected double enemySpawnCycle;
    protected int enemySpawnCounter;

    //Boss敌机生成，记录分数
    protected int bossSpawnTriggerScore;
    protected int bossSpawnScore;

    //英雄机射击周期
    protected double heroShootCycle;
    protected int heroShootCounter;

    //敌机射击周期
    protected double enemyShootCycle;
    protected int enemyShootCounter;

    //游戏对象列表
    protected HeroAircraft heroAircraft;
    protected List<EnemyAircraft> enemyAircrafts;
    protected List<BaseBullet> heroBullets;
    protected List<BaseBullet> enemyBullets;
    protected List<BaseItem> items;

    //观察者模式：炸弹和冰冻道具激活器
    protected BombActivate bombActivate;
    protected IceActivate iceActivate;

    //定时器
    protected Timer timer;
    protected int timeInterval;

    //当前玩家分数
    protected int score;

    //排行榜数据库
    protected DaoImpl playerScores;

    //游戏难度
    protected String difficulty;

    //游戏结束标志
    protected boolean gameOverFlag;

    // Game面板引用，用于重绘
    protected JPanel gamePanel;

    // 记录生成Boss的次数，用于衡量游戏时间
    protected int bossSpawnCount = 0;

    // 当前难度系数（1.0为基准）
    protected double difficultyFactor = 1.0;

    /**
     * 构造函数，初始化基本参数
     */
    public AbstractAction(String difficulty, HeroAircraft heroAircraft,
                          List<EnemyAircraft> enemyAircrafts,
                          List<BaseBullet> heroBullets,
                          List<BaseBullet> enemyBullets,
                          List<BaseItem> items,
                          DaoImpl playerScores,
                          JPanel gamePanel) {
        this.difficulty = difficulty;
        this.heroAircraft = heroAircraft;
        this.enemyAircrafts = enemyAircrafts;
        this.heroBullets = heroBullets;
        this.enemyBullets = enemyBullets;
        this.items = items;
        this.playerScores = playerScores;
        this.gamePanel = gamePanel;
        this.score = 0;
        this.gameOverFlag = false;
        this.enemySpawnCounter = 0;
        this.heroShootCounter = 0;
        this.enemyShootCounter = 0;
        this.bossSpawnScore = 0;

        // 初始化观察者
        this.bombActivate = new BombActivate();
        this.iceActivate = new IceActivate();

        // 设置时间间隔
        this.timeInterval = 40;

        // 初始化定时器
        this.timer = new Timer("game-action-timer", true);

        // 子类设置具体参数
        initParameters();
    }

    /**
     * 初始化不同难度的参数，由子类实现
     */
    protected abstract void initParameters();

    /**
     * 敌机生成逻辑，由子类实现不同难度的生成概率
     */
    protected abstract void EnemySpawnAction();

    /**
     * Boss生成逻辑，由子类实现不同难度的Boss生成策略
     */
    protected abstract void BossSpawnAction();

    /**
     * 更新射击周期，由子类实现不同难度的射击周期调整策略
     */
    protected abstract void updateShootCycles();

    /**
     * 游戏主逻辑入口
     */
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
                if (gamePanel != null) {
                    gamePanel.repaint();
                }
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task, 0, timeInterval);
    }

    //***********************
    //      Action 各部分
    //***********************

    /**
     * 计算当前难度系数
     * 每生成一次Boss，难度系数增加0.1，最大不超过3.0
     */
    protected void updateDifficultyFactor() {
        // 基础难度系数为1.0，每次Boss生成增加0.1
        this.difficultyFactor = 1.0 + (bossSpawnCount * 0.1);
        // 限制最大难度系数为3.0
        if (this.difficultyFactor > 3.0) {
            this.difficultyFactor = 3.0;
        }
    }

    protected void checkBossDeath() {
        boolean hasBoss = false;
        for (EnemyAircraft enemy : enemyAircrafts) {
            if (enemy instanceof BossEnemy) {
                hasBoss = true;
                break;
            }
        }

        if (!hasBoss && musicController.isPlaying(MusicType.BGM_BOSS)) {
            musicController.stopMusic(MusicType.BGM_BOSS);
            musicController.playBgm();
            musicController.enableBgmLoop(true);
        }
    }

    protected void shootAction() {
        // 英雄机射击
        heroShootCounter++;
        if (heroShootCounter >= heroShootCycle) {
            heroShootCounter = 0;
            heroBullets.addAll(heroAircraft.shoot());
        }

        // 敌机射击
        enemyShootCounter++;
        if (enemyShootCounter >= enemyShootCycle) {
            enemyShootCounter = 0;
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    protected void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    protected void aircraftsMoveAction() {
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    protected void itemsMoveAction() {
        for (BaseItem item : items) {
            item.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    protected void crashCheckAction() {
        // 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (bullet.crash(heroAircraft)) {
                // 敌机撞击到英雄机
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                musicController.playSoundEffect(MusicType.BULLET_HIT);
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        score += enemyAircraft.getScoreValue();
                        bossSpawnScore += enemyAircraft.getScoreValue();
                        items.addAll(enemyAircraft.generateItem());
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (BaseItem item : items) {
            if (item.notValid()) {
                continue;
            }
            if (item.crash(heroAircraft)) {
                // 设置道具的观察者
                if (item instanceof BombItem) {
                    ((BombItem) item).setBombActivate(bombActivate);
                } else if (item instanceof IceItem) {
                    ((IceItem) item).setIceActivate(iceActivate);
                }
                // 在激活前先更新观察者列表，确保当前所有敌机和子弹都被注册
                updateObservers();
                item.activate();
                musicController.playSoundEffect(MusicType.GET_SUPPLY);

                // 如果是bomb道具生效，播放爆炸音效并处理加分
                if (item instanceof BombItem) {
                    musicController.playSoundEffect(MusicType.BOMB_EXPLOSION);
                    // 处理被炸弹击毁的敌机：加分但不产生道具
                    for (EnemyAircraft enemy : bombActivate.getBombedEnemies()) {
                        if (enemy.notValid()) {
                            // 敌机已被炸弹击毁，加分
                            score += enemy.getScoreValue();
                            bossSpawnScore += enemy.getScoreValue();
                            // 注意：不调用generateItem()，所以不会产生道具
                        }
                    }
                }
                item.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    protected void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        items.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 更新观察者列表：
     * 将所有敌机和敌机子弹注册到炸弹和冰冻道具的观察者列表中
     */
    protected void updateObservers() {
        // 清空观察者列表
        bombActivate.observers.clear();
        iceActivate.observers.clear();

        // 注册所有敌机
        for (EnemyAircraft enemy : enemyAircrafts) {
            bombActivate.registerObserver(enemy);
            iceActivate.registerObserver(enemy);
        }

        // 注册所有敌机子弹
        for (BaseBullet bullet : enemyBullets) {
            if (bullet instanceof EnemyBullet) {
                bombActivate.registerObserver((Observer) bullet);
                iceActivate.registerObserver((Observer) bullet);
            }
        }
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    protected void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over!");

            musicController.stopAllMusic();
            musicController.playSoundEffect(MusicType.GAME_OVER);

            // 在事件调度线程中显示输入对话框并保存数据
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // 弹出窗口提示用户输入 PlayerName
                    String playerName = "Player"; // 默认名称

                    String input = JOptionPane.showInputDialog(
                            null,
                            "游戏结束！\n您的得分: " + score + "\n请输入您的名字:",
                            "游戏结束",
                            JOptionPane.QUESTION_MESSAGE
                    );

                    // 如果用户输入了名字，使用输入的名字；否则使用默认名
                    if (input != null && !input.trim().isEmpty()) {
                        playerName = input.trim();
                    }

                    // 获取当前时间
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    playerScores.update(new PlayerScore(playerName, score, time));

                    // 游戏结束后打开排行榜界面
                    Main.getGammingMode().switchToRankingList(difficulty);
                }
            });
        }
    }

    // Getter方法
    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOverFlag;
    }
}
