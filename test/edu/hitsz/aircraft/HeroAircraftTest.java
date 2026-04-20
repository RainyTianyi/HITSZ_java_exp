package edu.hitsz.aircraft;

import static org.junit.jupiter.api.Assertions.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        resetHeroAircraftInstance();
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
        resetHeroAircraftInstance();
    }

    private void resetHeroAircraftInstance() {
        try {
            java.lang.reflect.Field field = HeroAircraft.class.getDeclaredField("HeroAircraftInstance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            fail("Failed to reset HeroAircraft instance: " + e.getMessage());
        }
    }

    @Test
    void shoot() {
        List<BaseBullet> bullets = heroAircraft.shoot();

        assertNotNull(bullets, "射击返回的子弹列表不应为null");
        assertFalse(bullets.isEmpty(), "射击应该产生至少一颗子弹");

        BaseBullet bullet = bullets.getFirst();
        assertNotNull(bullet, "子弹对象不应为null");
        // 验证子弹属性，是 HeroBullet 不是 EnemyBullet
        assertInstanceOf(HeroBullet.class, bullet, "子弹对象应该是HeroBullet");
    }

    @Test
    void increaseHp() {
        int initialHp = heroAircraft.getHp();
        assertEquals(300, initialHp, "初始生命值应为300");

        heroAircraft.increaseHp(50);
        assertEquals(300, heroAircraft.getHp(),
                "生命值不应超过最大值300");

        heroAircraft.decreaseHp(100);
        assertEquals(200, heroAircraft.getHp(), "生命值应为200");

        heroAircraft.increaseHp(50);
        assertEquals(250, heroAircraft.getHp(), "增加50后生命值应为250");

        heroAircraft.increaseHp(100);
        assertEquals(300, heroAircraft.getHp(),
                "生命值不能超过最大值300");
    }

    @Test
    void crash() {
        MobEnemy enemy = new MobEnemy(
                heroAircraft.getLocationX(),
                heroAircraft.getLocationY() + 10,
                0, 10, 50
        );

        assertTrue(heroAircraft.crash(enemy),
                "当敌机距离很近时应该检测到碰撞");

        MobEnemy farEnemy = new MobEnemy(
                heroAircraft.getLocationX() + 500,
                heroAircraft.getLocationY() + 500,
                0, 10, 50
        );
        assertFalse(heroAircraft.crash(farEnemy),
                "当敌机距离很远时不应该检测到碰撞");
    }

    @Test
    void notValid() {
        assertFalse(heroAircraft.notValid(),
                "新创建的英雄机应该是有效的");

        heroAircraft.vanish();
        assertTrue(heroAircraft.notValid(),
                "调用vanish后应该标记为无效");
    }

    @Test
    void decreaseHp() {
        assertEquals(300, heroAircraft.getHp(), "初始生命值应为300");

        heroAircraft.decreaseHp(50);
        assertEquals(250, heroAircraft.getHp(), "减少50后生命值应为250");

        heroAircraft.decreaseHp(100);
        assertEquals(150, heroAircraft.getHp(), "再减少100后生命值应为150");

        heroAircraft.decreaseHp(150);
        assertEquals(0, heroAircraft.getHp(), "生命值降为0时应为0");
        assertTrue(heroAircraft.notValid(),
                "生命值为0时应该标记为无效");

        heroAircraft.decreaseHp(50);
        assertEquals(0, heroAircraft.getHp(),
                "生命值不能为负数，应保持为0");
    }
}
