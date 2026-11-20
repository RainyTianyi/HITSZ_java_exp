package edu.hitsz.android.game.bullet;

import edu.hitsz.android.game.Sprite;

/**
 * 英雄机子弹
 * @Author hitsz
 */
public class HeroBullet extends BaseBullet {

    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(Sprite.HERO_BULLET, locationX, locationY, speedX, speedY, power);
    }

}
