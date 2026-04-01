package edu.hitsz.aircraft;

import edu.hitsz.item.BaseItem;

import java.util.List;

public abstract class EnemyAircraft extends AbstractAircraft{

    // 敌机分值
    protected int scoreValue;

    // 敌机射击间隔
    protected int shootCycle;

    // 敌机能否射击
    protected boolean canShoot;

    // 敌机是否会掉落道具
    protected boolean canDropItem;

    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp
    , int scoreValue, int shootCycle, boolean canShoot, boolean canDropItem) {
        super(locationX, locationY, speedX, speedY, hp);
        this.scoreValue = scoreValue;
        this.shootCycle = shootCycle;
        this.canShoot = canShoot;
        this.canDropItem = canDropItem;
    }

    // 掉落道具
    public abstract List<BaseItem> generateItem();

    public int getScoreValue() {
        return scoreValue;
    }
}
