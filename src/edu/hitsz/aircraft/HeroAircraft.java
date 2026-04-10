package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategy.ShootPattern;
import edu.hitsz.ShootStrategy.ShootStrategy;
import edu.hitsz.ShootStrategy.SingleShootStrategy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    //子弹威力
    private static final int power = 30;

    //子弹射击方向 (向上发射：-1，向下发射：1)
    private static final int direction = -1;

    // 发射策略
    public ShootPattern shootPattern;

    // 单一英雄机实例
    private static HeroAircraft HeroAircraftInstance;

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootPattern = new ShootPattern(shootStrategy);
    }

    public static synchronized HeroAircraft getHeroAircraft() {
        if (HeroAircraftInstance == null) {
            HeroAircraftInstance = new HeroAircraft(
                    Main.WINDOW_WIDTH / 2,
                    Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                    0, 0, 300, new SingleShootStrategy());
        }
        return HeroAircraftInstance;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return shootPattern.shoot(this.locationX, this.locationY, direction, power, this.speedY);
    }

    public void increaseHp(int increase){
        this.hp = Math.min(hp + increase, maxHp);
    }
}
