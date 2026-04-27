package edu.hitsz.application;

import edu.hitsz.DAO.DaoImpl;
import edu.hitsz.DAO.PlayerScore;
import edu.hitsz.EnemyFactory.*;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.BombItem;
import edu.hitsz.music.MusicController;
import edu.hitsz.music.MusicType;

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

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //Boss敌机生成，记录分数
    private int bossSpawnScore = 0;

    //英雄机和敌机射击周期
    protected double shootCycle = 20;
    private int shootCounter = 0;

    //当前玩家分数
    private int score = 0;

    //排行榜数据库
    private final DaoImpl playerScores = new DaoImpl();

    //游戏结束标志
    private boolean gameOverFlag = false;

    //音频管理
    private MusicController musicController = new MusicController();


    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        items = new LinkedList<>();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
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
                    EnemyAircraftFactory enemyAircraftFactory;
                    // 随机产生除BossEnemy之外的其他Enemy
                    int type = (int) (Math.random() * 100);
                    if (enemyAircrafts.size() < enemyMaxNumber) {
                        if (type < 40) {
                            enemyAircraftFactory = new MobEnemyFactory();
                            enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                        }
                        else if (type < 70){
                            // 随机产生精英敌机的横向速度，在[-10, 10]之间
                            enemyAircraftFactory = new EliteEnemyFactory();
                            enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                        }
                        else if (type < 90){
                            enemyAircraftFactory = new CrackEnemyFactory();
                            enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                        }
                        else{
                            enemyAircraftFactory = new AceEnemyFactory();
                            enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                        }
                    }
                }

                // 积分每到达1000分且当前不存在Boss敌机时，生成Boss敌机
                if (bossSpawnScore >= 500) {
                    boolean hasBoss = false;
                    for (EnemyAircraft enemy : enemyAircrafts) {
                        if (enemy instanceof BossEnemy) {
                            hasBoss = true;
                            break;
                        }
                    }
                    if (!hasBoss) {
                        EnemyAircraftFactory enemyAircraftFactory = new BossEnemyFactory();
                        enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                        bossSpawnScore = 0;
                        musicController.playBossBgm();
                        musicController.enableBgmLoop(true);
                    }
                }

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
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void checkBossDeath() {
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
    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            //英雄机射击
            heroBullets.addAll(heroAircraft.shoot());
            //敌机射击
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void itemsMoveAction() {
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
    private void crashCheckAction() {
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
                item.activate();
                musicController.playSoundEffect(MusicType.GET_SUPPLY);

                // 如果是bomb道具生效，播放爆炸音效
                if (item instanceof BombItem) {
                    musicController.playSoundEffect(MusicType.BOMB_EXPLOSION);
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
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        items.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
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
                    Main.getGammingMode().switchToRankingList();
                }
            });
        }
    }

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
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
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
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
