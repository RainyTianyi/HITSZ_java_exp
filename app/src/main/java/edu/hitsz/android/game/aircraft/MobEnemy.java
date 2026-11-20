package edu.hitsz.android.game.aircraft;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.bullet.BaseBullet;
import edu.hitsz.android.game.item.BaseItem;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class MobEnemy extends EnemyAircraft {

    // 普通敌机得分值
    private static final int scoreValue = 10;

    // 普通敌机发射子弹的间隔
    private static final int shootCycle = 40;

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp)
    {
        super(Sprite.MOB, locationX, locationY, speedX, speedY, hp, scoreValue, shootCycle);
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    @Override
    public List<BaseItem> generateItem() { return new LinkedList<>(); }

    @Override
    public void beBombed() {
        // 普通敌机被炸弹击中，直接坠毁
        vanish();
    }

    @Override
    public void beIced() {
        // 普通敌机被冰冻，永久静止
        speedX = 0;
        speedY = 0;
    }
}
