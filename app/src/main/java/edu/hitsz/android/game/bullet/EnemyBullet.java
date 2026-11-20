package edu.hitsz.android.game.bullet;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.observer.Observer;


/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Observer {
    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(Sprite.ENEMY_BULLET, locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void beBombed()
    {
        // 炸弹道具生效，敌机子弹直接消失
        vanish();
    }

    @Override
    public void beIced()
    {
        freezeFor(5000, Integer.MAX_VALUE);
    }
}
