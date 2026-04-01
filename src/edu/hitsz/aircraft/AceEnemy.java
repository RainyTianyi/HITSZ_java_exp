package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.ItemFactory;

import java.util.LinkedList;
import java.util.List;

public class AceEnemy extends EnemyAircraft{

    // TODO 改UML图
    // 默认每次发射三枚子弹
    private int shootNum = 3;

    // 子弹威力
    private int power = 30;

    // 子弹射击方向 (向上发射：-1，向下发射：1)
    private int direction = 1;

    // 子弹速度
    private int speed = 10;

    // 扇形半顶角
    private int angle = 30;

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp
                , 50, 20, true, true);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    // TODO: 王牌敌机一定会掉落五种道具中的一种
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        BaseBullet bullet;

        // 生成三个子弹的角度
        int[] angles = new int[]{-angle, 0, angle};
        // 子弹速度矢量为speed，角度为-angel, 0, angel
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int speedX = (int) (speed * Math.sin(Math.toRadians(angles[i])));
            int speedY = (int) (speed * Math.cos(Math.toRadians(angles[i])));
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*20, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }

    @Override
    public List<BaseItem> generateItem() {
        // 王牌敌机一定会掉落一种道具
        List<BaseItem> res = new LinkedList<>();

        // 道具的位置和速度
        int x = this.getLocationX();
        int y = this.getLocationY();
        int speedX = 0;
        int speedY = 10;

        int itemType = (int) (Math.random() * 5);
        String itemTypeName;
        int itemValue;

        switch (itemType) {
            case 0:
                itemTypeName = ItemFactory.TYPE_BLOOD;
                itemValue = 20;
                break;
            case 1:
                itemTypeName = ItemFactory.TYPE_FIRE;
                itemValue = 0;
                break;
            case 2:
                itemTypeName = ItemFactory.TYPE_FIRE_PLUS;
                itemValue = 0;
                break;
            case 3:
                itemTypeName = ItemFactory.TYPE_BOMB;
                itemValue = 50;
                break;
            case 4:
                itemTypeName = ItemFactory.TYPE_ICE;
                itemValue = 100;
                break;
            default:
                return res;
        }

        BaseItem item = ItemFactory.createItem(itemTypeName, x, y, speedX, speedY, itemValue);
        res.add(item);
        return res;
    }
}
