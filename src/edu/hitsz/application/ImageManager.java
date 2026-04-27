package edu.hitsz.application;


import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.item.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage BACKGROUND_IMAGE_2;
    public static BufferedImage BACKGROUND_IMAGE_3;
    public static BufferedImage BACKGROUND_IMAGE_4;
    public static BufferedImage BACKGROUND_IMAGE_5;

    public static BufferedImage HERO_IMAGE;

    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;

    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage CRACK_ENEMY_IMAGE;
    public static BufferedImage ACE_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;

    public static BufferedImage BLOOD_ITEM_IMAGE;
    public static BufferedImage FIRE_ITEM_IMAGE;
    public static BufferedImage FIRE_PLUS_ITEM_IMAGE;
    public static BufferedImage BOMB_ITEM_IMAGE;
    public static BufferedImage ICE_ITEM_IMAGE;

    private static BufferedImage currentBackgroundImage;


    static {
        try {
            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_IMAGE_2 = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_IMAGE_3 = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            BACKGROUND_IMAGE_4 = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
            BACKGROUND_IMAGE_5 = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));

            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));

            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            CRACK_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            ACE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePro.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));

            BLOOD_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            FIRE_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            FIRE_PLUS_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));
            BOMB_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            ICE_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_freeze.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);

            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);

            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(CrackEnemy.class.getName(), CRACK_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(AceEnemy.class.getName(), ACE_ENEMY_IMAGE);

            CLASSNAME_IMAGE_MAP.put(BloodItem.class.getName(), BLOOD_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FireItem.class.getName(), FIRE_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FirePlusItem.class.getName(), FIRE_PLUS_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BombItem.class.getName(), BOMB_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(IceItem.class.getName(), ICE_ITEM_IMAGE);

            currentBackgroundImage = BACKGROUND_IMAGE;


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void setBackgroundByDifficulty(String difficulty) {
        if ("simple".equalsIgnoreCase(difficulty)) {
            currentBackgroundImage = BACKGROUND_IMAGE;
        } else if ("normal".equalsIgnoreCase(difficulty)) {
            currentBackgroundImage = BACKGROUND_IMAGE_2;
        } else if ("hard".equalsIgnoreCase(difficulty)) {
            currentBackgroundImage = BACKGROUND_IMAGE_3;
        } else {
            currentBackgroundImage = BACKGROUND_IMAGE;
        }
    }

    public static BufferedImage getCurrentBackgroundImage() {
        return currentBackgroundImage;
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}
