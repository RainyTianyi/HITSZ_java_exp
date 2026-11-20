package edu.hitsz.android.game.aircraft;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.shootstrategy.CircleShootStrategy;
import edu.hitsz.android.game.shootstrategy.ShootPattern;
import edu.hitsz.android.game.bullet.BaseBullet;
import edu.hitsz.android.game.item.BaseItem;
import edu.hitsz.android.game.item.ItemFactory;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends EnemyAircraft{

    // boss敌机得分值
    private static final int scoreValue = 100;

    // boss敌机发射子弹的间隔
    private static final int shootCycle = 40;

    // 子弹射击方向 (向上发射：-1，向下发射：1)
    private static final int direction = 1;

    // 子弹威力
    private static final int power = 30;

    // boss敌机发射子弹的策略
    private static final ShootPattern shootPattern = new ShootPattern(new CircleShootStrategy());

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(Sprite.BOSS, locationX, locationY, speedX, speedY, hp, scoreValue, shootCycle);
    }


    @Override
    public List<BaseBullet> shoot() {
        return shootPattern.shoot(locationX, locationY, direction, power, getSpeedY());
    }

    @Override
    public List<BaseItem> generateItem() {
        // Boss敌机随机掉落三种道具
        List<BaseItem> res = new LinkedList<>();

        // 道具从敌机位置生成
        int x = this.getLocationX();
        int y = this.getLocationY();

        for (int i = 0; i < 3; i++) {
            int itemType = (int) (Math.random() * 5);
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
                case 3:
                    itemTypeName = ItemFactory.TYPE_BOMB;
                    break;
                case 4:
                    itemTypeName = ItemFactory.TYPE_ICE;
                    break;
                default:
                    return res;
            }

            BaseItem item = ItemFactory.createItem(itemTypeName, x + i * 20, y + (i-1) * 20);
            res.add(item);
        }

        return res;
    }

    @Override
    public void beBombed() {
        // Boss敌机不受炸弹影响
    }

    @Override
    public void beIced() {
        // Boss敌机不受冰冻影响
    }
}
