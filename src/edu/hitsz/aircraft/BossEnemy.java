package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.item.BaseItem;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends EnemyAircraft{

    // boss敌机得分值
    private static final int scoreValue = 100;

    // boss敌机发射子弹的间隔
    private static final int shootCycle = 40;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        return new LinkedList<>();
    }

    @Override
    public List<BaseItem> generateItem() {
        return new LinkedList<>();
    }
}
