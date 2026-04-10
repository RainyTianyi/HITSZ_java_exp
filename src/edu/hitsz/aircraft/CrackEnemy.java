package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategy.DoubleShootStrategy;
import edu.hitsz.ShootStrategy.ShootPattern;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.ItemFactory;

import java.util.LinkedList;
import java.util.List;

public class CrackEnemy extends EnemyAircraft{

    // 精锐敌机得分值
    private static final int scoreValue = 30;

    // 精锐敌机发射子弹的间隔
    private static final int shootCycle = 30;

    // 子弹威力
    private static final int power = 30;

    // 子弹射击方向 (向上发射：-1，向下发射：1)
    private static final int direction = 1;

    // 子弹发射策略
    private static final ShootPattern shootPattern = new ShootPattern(new DoubleShootStrategy());

    // 精锐敌机随机发射道具的概率
    // TODO 改UML图
    private static final double PROBABILITY = 0.80;

    public CrackEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, scoreValue, shootCycle);
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
        return shootPattern.shoot(locationX, locationY, direction, power, speedY);
    }

    @Override
    public List<BaseItem> generateItem() {
        // 精锐敌机坠毁时，以一定概率随机生成一个道具
        // 范围限定为：加血道具、火力道具、超级火力道具、炸弹道具
        List<BaseItem> res = new LinkedList<>();
        if (Math.random() < PROBABILITY) {

            // 道具从敌机位置生成
            int x = this.getLocationX();
            int y = this.getLocationY();

            // 随机选择一种道具生成
            int itemType = (int) (Math.random() * 4);
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
                default:
                    return res;
            }

            BaseItem item = ItemFactory.createItem(itemTypeName, x, y);
            res.add(item);
        }
        return res;
    }
}
