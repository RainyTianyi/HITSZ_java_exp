package edu.hitsz.android.game;

public enum Difficulty {
    SIMPLE(4, 25, 30, 0, 60, 80, 95),
    NORMAL(5, 20, 20, 600, 40, 70, 90),
    HARD(7, 15, 12, 500, 25, 60, 85);

    // 出敌和射击间隔以对局步数计，取自桌面版三档 Action。
    final int maxEnemies;
    final int spawnInterval;
    final int enemyShotInterval;
    final int bossScore;
    final int mobChance;
    final int eliteChance;
    final int crackChance;

    Difficulty(int maxEnemies, int spawnInterval, int enemyShotInterval, int bossScore,
               int mobChance, int eliteChance, int crackChance) {
        this.maxEnemies = maxEnemies;
        this.spawnInterval = spawnInterval;
        this.enemyShotInterval = enemyShotInterval;
        this.bossScore = bossScore;
        this.mobChance = mobChance;
        this.eliteChance = eliteChance;
        this.crackChance = crackChance;
    }
}
