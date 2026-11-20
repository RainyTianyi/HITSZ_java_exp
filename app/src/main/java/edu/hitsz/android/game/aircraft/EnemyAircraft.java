package edu.hitsz.android.game.aircraft;

import edu.hitsz.android.game.Arena;
import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.item.BaseItem;
import edu.hitsz.android.game.observer.Observer;

import java.util.List;

public abstract class EnemyAircraft extends AbstractAircraft implements Observer {

    // 敌机分值
    protected int scoreValue;

    // 敌机射击间隔
    protected int shootCycle;

    public EnemyAircraft(Sprite sprite, int locationX, int locationY, int speedX, int speedY, int hp
    , int scoreValue, int shootCycle) {
        super(sprite, locationX, locationY, speedX, speedY, hp);
        this.scoreValue = scoreValue;
        this.shootCycle = shootCycle;
    }

    @Override
    public void forward() {
        super.forward();
        int halfWidth = getWidth() / 2;
        if (locationX < halfWidth) {
            locationX = halfWidth;
            speedX = Math.abs(speedX);
        } else if (locationX > Arena.WIDTH - halfWidth) {
            locationX = Arena.WIDTH - halfWidth;
            speedX = -Math.abs(speedX);
        }
        if (locationY >= Arena.HEIGHT) {
            vanish();
        }
    }

    // 掉落道具
    public abstract List<BaseItem> generateItem();

    public int getScoreValue() {
        return scoreValue;
    }
}
