package edu.hitsz.item;

import edu.hitsz.application.Main;

public class FirePlusItem extends BaseItem{

    public FirePlusItem(int locationX, int locationY, int speedX, int speedY, int duration) {
        super(locationX, locationY, speedX, speedY, duration, 0);
    }

    @Override
    public void activate() {
        // 暂不做实现，打印信息"FireItem active!"
        // TODO 实现火力单元 注意更改上方的duration
        System.out.println("FirePlusItem active!");
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
