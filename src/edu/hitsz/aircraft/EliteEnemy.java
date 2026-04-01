package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 可射击、掉落道具
 * @author hitsz
 */
public class EliteEnemy extends EnemyAircraft{

    // 默认每次发射一枚子弹

    //子弹威力
    private int power = 30;

    //子弹射击方向 (向上发射：-1，向下发射：1)
    private int direction = 1;

    // 精英敌机随机发射道具的概率
    // TODO 改UML图
    private static final double PROBABILITY = 0.80;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp
        , 20, 40, true, true);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet bullet;
        bullet = new EnemyBullet(x, y, speedX, speedY, power);
        res.add(bullet);
        return res;
    }

    @Override
    public List<BaseItem> generateItem() {
        // 精英敌机坠毁时，以一定概率随机生成一个道具
        // 范围限定为：加血道具、火力道具、超级火力道具
        List<BaseItem> res = new LinkedList<>();
        if (Math.random() < PROBABILITY) {
            int x = this.getLocationX();
            int y = this.getLocationY();
            int speedX = 0;
            int speedY = 10;
            // 随机选择一种道具生成
            int itemType = (int) (Math.random() * 3);
            String itemTypeName;
            int itemValue;

            switch (itemType) {
                case 0:
                    itemTypeName = ItemFactory.TYPE_BLOOD;
                    itemValue = 20;
                    break;
                case 1:
                    itemTypeName = ItemFactory.TYPE_FIRE;
                    itemValue = 20;
                    break;
                case 2:
                    itemTypeName = ItemFactory.TYPE_FIRE_PLUS;
                    itemValue = 40;
                    break;
                default:
                    return res;
            }

            BaseItem item = ItemFactory.createItem(itemTypeName, x, y, speedX, speedY, itemValue);
            res.add(item);
        }
        return res;
    }

}
