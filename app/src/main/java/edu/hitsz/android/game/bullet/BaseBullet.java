package edu.hitsz.android.game.bullet;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.Arena;
import edu.hitsz.android.game.basic.AbstractFlyingObject;

/**
 * 子弹基类
 * @author hitsz
 */
public abstract class BaseBullet extends AbstractFlyingObject {

    private int power = 0;

    public BaseBullet(Sprite sprite, int locationX, int locationY, int speedX, int speedY, int power) {
        super(sprite, locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();

        if (locationX <= 0 || locationX >= Arena.WIDTH
                || locationY <= 0 || locationY >= Arena.HEIGHT) {
            vanish();
        }
    }

    public int getPower() {
        return power;
    }
}
