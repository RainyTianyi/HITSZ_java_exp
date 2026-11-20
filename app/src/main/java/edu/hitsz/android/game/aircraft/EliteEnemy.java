package edu.hitsz.android.game.aircraft;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.shootstrategy.ShootPattern;
import edu.hitsz.android.game.shootstrategy.SingleShootStrategy;
import edu.hitsz.android.game.bullet.BaseBullet;
import edu.hitsz.android.game.item.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 可射击、掉落道具
 * @author hitsz
 */
public class EliteEnemy extends EnemyAircraft{

    // 精英敌机得分值
    private static final int scoreValue = 20;

    // 精英敌机发射子弹的间隔
    private static final int shootCycle = 20;

    //子弹威力
    private final static int power = 30;

    //子弹射击方向 (向上发射：-1，向下发射：1)
    private final static int direction = 1;

    // 子弹射击策略
    private static final ShootPattern shootPattern = new ShootPattern(new SingleShootStrategy());

    // 精英敌机随机发射道具的概率
    private static final double PROBABILITY = 0.50;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(Sprite.ELITE, locationX, locationY, speedX, speedY, hp, scoreValue, shootCycle);
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootPattern.shoot(locationX, locationY, direction, power, getSpeedY());
    }

    @Override
    public List<BaseItem> generateItem() {
        // 精英敌机坠毁时，以一定概率随机生成一个道具
        // 范围限定为：加血道具、火力道具、超级火力道具
        List<BaseItem> res = new LinkedList<>();
        if (Math.random() < PROBABILITY) {
            // 道具从敌机位置生成
            int x = this.getLocationX();
            int y = this.getLocationY();
            // 随机选择一种道具生成
            int itemType = (int) (Math.random() * 3);
            String itemTypeName;

            switch (itemType) {
                case 0:
                    itemTypeName = ItemFactory.TYPE_BLOOD;
                    break;
                case 1:
                    itemTypeName = ItemFactory.TYPE_FIRE;
                    break;
                case 2:
                    itemTypeName = ItemFactory.TYPE_FIRE_PLUS;
                    break;
                default:
                    return res;
            }

            BaseItem item = ItemFactory.createItem(itemTypeName, x, y);
            res.add(item);
        }
        return res;
    }

    @Override
    public void beBombed() {
        // 精英敌机被炸弹击中，直接坠毁
        vanish();
    }

    @Override
    public void beIced() {
        freezeFor(4000, Integer.MAX_VALUE);
    }
}
