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

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;

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


    static {
        try {
            // 加载背景图片
            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));

            // 加载英雄机图片
            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));

            // 加载子弹图片
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));

            // 加载敌机图片
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            CRACK_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            ACE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePro.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));

            // 加载道具图片
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


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
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
