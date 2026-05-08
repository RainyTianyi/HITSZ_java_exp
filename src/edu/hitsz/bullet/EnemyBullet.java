package edu.hitsz.bullet;

import edu.hitsz.observer.Observer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Observer {
    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void beBombed()
    {
        // 炸弹道具生效，敌机子弹直接消失
        vanish();
    }

    @Override
    public void beIced()
    {
        // 子弹停止 5 秒后恢复运动
        int originalSpeedX = this.speedX;
        int originalSpeedY = this.speedY;
        this.speedX = 0;
        this.speedY = 0;
        
        // 5秒后恢复原速度
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                speedX = originalSpeedX;
                speedY = originalSpeedY;
                timer.cancel();
            }
        }, 5000);
    }
}
