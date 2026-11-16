package edu.hitsz.android.game;

/** 原版素材在游戏逻辑坐标中的尺寸。 */
public enum Sprite {
    HERO(100, 83),
    MOB(75, 50),
    ELITE(105, 68),
    CRACK(105, 68),
    ACE(100, 83),
    BOSS(300, 200),
    HERO_BULLET(10, 25),
    ENEMY_BULLET(10, 18),
    BLOOD(33, 30),
    FIRE(35, 33),
    FIRE_PLUS(34, 34),
    BOMB(34, 34),
    ICE(35, 35);

    public final int width;
    public final int height;

    Sprite(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
