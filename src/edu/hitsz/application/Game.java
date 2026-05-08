package edu.hitsz.application;

import edu.hitsz.DAO.DaoImpl;
import edu.hitsz.DAO.PlayerScore;
import edu.hitsz.EnemyFactory.*;
import edu.hitsz.action.AbstractAction;
import edu.hitsz.action.HardAction;
import edu.hitsz.action.NormalAction;
import edu.hitsz.action.SimpleAction;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.BombItem;
import edu.hitsz.music.MusicController;
import edu.hitsz.music.MusicType;
import edu.hitsz.item.IceItem;
import edu.hitsz.observer.BombActivate;
import edu.hitsz.observer.IceActivate;
import edu.hitsz.observer.ItemActivate;
import edu.hitsz.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<EnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseItem> items;

    //当前玩家分数
    private int score = 0;

    //排行榜数据库
    private final DaoImpl playerScores;

    //游戏难度
    private final String difficulty;

    //游戏结束标志
    private boolean gameOverFlag = false;

    //音频管理
    private MusicController musicController = new MusicController();

    // Action对象，负责执行游戏逻辑
    private AbstractAction action;


    public Game(String difficulty) {
        this.difficulty = difficulty;
        ImageManager.setBackgroundByDifficulty(difficulty);

        this.playerScores = new DaoImpl(difficulty);
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        items = new LinkedList<>();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

        // 根据难度创建对应的Action对象
        switch (difficulty.toLowerCase()) {
            case "simple":
                action = new SimpleAction(difficulty, heroAircraft, enemyAircrafts, 
                        heroBullets, enemyBullets, items, playerScores, this);
                break;
            case "hard":
                action = new HardAction(difficulty, heroAircraft, enemyAircrafts,
                        heroBullets, enemyBullets, items, playerScores, this);
                break;
            default:
                action = new NormalAction(difficulty, heroAircraft, enemyAircrafts,
                        heroBullets, enemyBullets, items, playerScores, this);
                break;
        }
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */

    public void action() {
        // 委托给Action对象执行游戏逻辑
        action.action();
    }

    //***********************
    //      Action 各部分
    //***********************

    // 所有游戏逻辑已移至AbstractAction及其子类中

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        BufferedImage currentBg = ImageManager.getCurrentBackgroundImage();
        g.drawImage(currentBg, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(currentBg, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        // 绘制道具
        paintImageWithPositionRevised(g, items);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        // 从Action对象获取分数
        if (action != null) {
            g.drawString("SCORE: " + action.getScore(), x, y);
        } else {
            g.drawString("SCORE: " + this.score, x, y);
        }
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
