package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.item.BaseItem;

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
        return new LinkedList<>();
    }

}
