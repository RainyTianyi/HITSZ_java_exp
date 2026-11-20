package edu.hitsz.android.game.aircraft;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.shootstrategy.ShootPattern;
import edu.hitsz.android.game.shootstrategy.ShootStrategy;
import edu.hitsz.android.game.shootstrategy.SingleShootStrategy;
import edu.hitsz.android.game.Arena;
import edu.hitsz.android.game.bullet.BaseBullet;
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

    private final ShootPattern shootPattern = new ShootPattern(new SingleShootStrategy());
    private int powerUpRemaining;

    // 每局创建自己的英雄机，重开后生命值、位置和火力从初始状态开始。
    public HeroAircraft() {
        super(Sprite.HERO, Arena.WIDTH / 2, Arena.HEIGHT - Sprite.HERO.height, 0, 0, 10000);
    }

    public void moveTo(float x, float y) {
        float halfWidth = getWidth() / 2f;
        float halfHeight = getHeight() / 2f;
        locationX = Math.round(Math.max(halfWidth, Math.min(x, Arena.WIDTH - halfWidth)));
        locationY = Math.round(Math.max(halfHeight, Math.min(y, Arena.HEIGHT - halfHeight)));
    }

    public void powerUp(ShootStrategy strategy, int duration) {
        shootPattern.setTemporaryStrategy(strategy);
        powerUpRemaining = duration;
    }

    @Override
    public void forward() {
        if (powerUpRemaining > 0) {
            powerUpRemaining -= Arena.STEP_MS;
            if (powerUpRemaining <= 0) {
                shootPattern.restoreOriginalStrategy();
            }
        }
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
        if (!notValid()) {
            hp = Math.min(hp + increase, maxHp);
        }
    }
}
