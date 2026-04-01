package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.ItemFactory;

import java.util.LinkedList;
import java.util.List;

public class CrackEnemy extends EnemyAircraft{

    // 默认每次发射两枚子弹
    private int shootNum = 2;

    //子弹威力
    private int power = 30;

    //子弹射击方向 (向上发射：-1，向下发射：1)
    private int direction = 1;

    // 精锐敌机随机发射道具的概率
    // TODO 改UML图
    private static final double PROBABILITY = 0.80;

    public CrackEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp
        , 30, 30, true, true);
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
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*20, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
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
