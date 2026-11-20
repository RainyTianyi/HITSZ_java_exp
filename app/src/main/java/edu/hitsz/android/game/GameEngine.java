package edu.hitsz.android.game;

import edu.hitsz.android.game.aircraft.EnemyAircraft;
import edu.hitsz.android.game.aircraft.HeroAircraft;
import edu.hitsz.android.game.basic.AbstractFlyingObject;
import edu.hitsz.android.game.bullet.BaseBullet;
import edu.hitsz.android.game.bullet.EnemyBullet;
import edu.hitsz.android.game.enemyfactory.AceEnemyFactory;
import edu.hitsz.android.game.enemyfactory.BossEnemyFactory;
import edu.hitsz.android.game.enemyfactory.CrackEnemyFactory;
import edu.hitsz.android.game.enemyfactory.EliteEnemyFactory;
import edu.hitsz.android.game.enemyfactory.EnemyAircraftFactory;
import edu.hitsz.android.game.enemyfactory.MobEnemyFactory;
import edu.hitsz.android.game.item.BaseItem;
import edu.hitsz.android.game.item.BombItem;
import edu.hitsz.android.game.item.IceItem;
import edu.hitsz.android.game.observer.BombActivate;
import edu.hitsz.android.game.observer.IceActivate;
import edu.hitsz.android.game.observer.ItemActivate;

import java.util.ArrayList;
import java.util.List;

/** 由 SurfaceView 的工作线程驱动，只处理一局内的规则和数据。 */
public final class GameEngine {
    private final Difficulty difficulty;
    private final HeroAircraft hero = new HeroAircraft();
    private final List<EnemyAircraft> enemies = new ArrayList<>();
    private final List<BaseBullet> heroBullets = new ArrayList<>();
    private final List<BaseBullet> enemyBullets = new ArrayList<>();
    private final List<BaseItem> items = new ArrayList<>();
    private final BombActivate bomb = new BombActivate();
    private final IceActivate ice = new IceActivate();

    private int spawnTicks;
    private int heroShotTicks;
    private int enemyShotTicks;
    private int score;
    private int bossProgress;
    private int bossCount;
    private EnemyAircraft boss;

    public GameEngine(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void step() {
        if (isOver()) {
            return;
        }
        // 延续原版顺序：生成敌机、射击、移动、碰撞、清除失效对象。
        hero.forward();
        if (++spawnTicks >= difficulty.spawnInterval) {
            spawnTicks = 0;
            spawnEnemy();
        }
        spawnBossIfReady();
        shoot();
        heroBullets.forEach(AbstractFlyingObject::forward);
        enemyBullets.forEach(AbstractFlyingObject::forward);
        enemies.forEach(AbstractFlyingObject::forward);
        items.forEach(AbstractFlyingObject::forward);
        checkCollisions();
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        enemies.removeIf(AbstractFlyingObject::notValid);
        items.removeIf(AbstractFlyingObject::notValid);

        // Boss 在场时暂停累计下一只 Boss 的分数，击毁后才开始新一轮累计。
        if (boss != null && boss.notValid()) {
            boss = null;
            bossProgress = 0;
        }
    }

    private void spawnEnemy() {
        if (enemies.size() >= difficulty.maxEnemies) {
            return;
        }
        int roll = (int) (Math.random() * 100);
        EnemyAircraftFactory factory;
        if (roll < difficulty.mobChance) {
            factory = new MobEnemyFactory();
        } else if (roll < difficulty.eliteChance) {
            factory = new EliteEnemyFactory();
        } else if (roll < difficulty.crackChance) {
            factory = new CrackEnemyFactory();
        } else {
            factory = new AceEnemyFactory();
        }
        enemies.add(factory.createEnemyAircraft(difficultyFactor()));
    }

    private void spawnBossIfReady() {
        if (difficulty.bossScore > 0 && bossProgress >= difficulty.bossScore && boss == null) {
            double bossFactor = difficulty == Difficulty.HARD ? difficultyFactor() : 1.0;
            boss = new BossEnemyFactory().createEnemyAircraft(bossFactor);
            enemies.add(boss);
            bossCount++;
            bossProgress = 0;
        }
    }

    private double difficultyFactor() {
        return Math.min(3.0, 1.0 + bossCount * 0.1);
    }

    private void shoot() {
        double heroInterval = 20;
        double enemyInterval = difficulty.enemyShotInterval;
        if (difficulty == Difficulty.HARD) {
            heroInterval = Math.max(10, heroInterval / difficultyFactor());
            enemyInterval = Math.max(5, enemyInterval / difficultyFactor());
        }
        if (++heroShotTicks >= heroInterval) {
            heroShotTicks = 0;
            heroBullets.addAll(hero.shoot());
        }
        if (++enemyShotTicks >= enemyInterval) {
            enemyShotTicks = 0;
            for (EnemyAircraft enemy : enemies) {
                enemyBullets.addAll(enemy.shoot());
            }
        }
    }

    private void checkCollisions() {
        for (BaseBullet bullet : enemyBullets) {
            if (!bullet.notValid() && !hero.notValid() && bullet.crash(hero)) {
                hero.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemy : enemies) {
                if (!enemy.notValid() && enemy.crash(bullet)) {
                    enemy.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemy.notValid()) {
                        addScore(enemy);
                        items.addAll(enemy.generateItem());
                    }
                    // 一发子弹只结算一次，同一敌机只在击毁时加分。
                    break;
                }
            }
        }
        // 机身碰撞独立于英雄子弹，开火前撞到敌机也会结束对局。
        for (EnemyAircraft enemy : enemies) {
            if (!hero.notValid() && !enemy.notValid() && enemy.crash(hero)) {
                enemy.vanish();
                hero.decreaseHp(hero.getHp());
            }
        }
        if (!hero.notValid()) {
            for (BaseItem item : items) {
                if (!item.notValid() && item.crash(hero)) {
                    collect(item);
                }
            }
        }
    }

    private void collect(BaseItem item) {
        if (item instanceof BombItem) {
            ((BombItem) item).setBombActivate(bomb);
            registerObservers(bomb);
        } else if (item instanceof IceItem) {
            ((IceItem) item).setIceActivate(ice);
            registerObservers(ice);
        }
        item.activate(hero);
        item.vanish();

        if (item instanceof BombItem) {
            for (EnemyAircraft enemy : bomb.getBombedEnemies()) {
                if (enemy.notValid()) {
                    // 炸弹击毁的敌机加分，沿用原版不再掉落道具的规则。
                    addScore(enemy);
                }
            }
        }
    }

    private void registerObservers(ItemActivate activation) {
        activation.observers.clear();
        for (EnemyAircraft enemy : enemies) {
            if (!enemy.notValid()) {
                activation.registerObserver(enemy);
            }
        }
        for (BaseBullet bullet : enemyBullets) {
            if (!bullet.notValid() && bullet instanceof EnemyBullet) {
                activation.registerObserver((EnemyBullet) bullet);
            }
        }
    }

    private void addScore(EnemyAircraft enemy) {
        score += enemy.getScoreValue();
        if (boss == null) {
            bossProgress += enemy.getScoreValue();
        }
    }

    public HeroAircraft hero() {
        return hero;
    }

    public Iterable<EnemyAircraft> enemies() {
        return enemies;
    }

    public Iterable<BaseBullet> heroBullets() {
        return heroBullets;
    }

    public Iterable<BaseBullet> enemyBullets() {
        return enemyBullets;
    }

    public Iterable<BaseItem> items() {
        return items;
    }

    public int score() {
        return score;
    }

    public boolean isOver() {
        return hero.notValid();
    }
}
