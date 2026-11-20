package edu.hitsz.android.game.basic;

import edu.hitsz.android.game.Sprite;

import edu.hitsz.android.game.aircraft.AbstractAircraft;
import edu.hitsz.android.game.Arena;


/**
 * 可飞行对象的基类
 * @author hitsz
 */
public abstract class AbstractFlyingObject {

    //locationX,locationY为图片中心位置坐标
    protected int locationX;
    protected int locationY;

    // x,y轴移动速度
    protected int speedX;
    protected int speedY;

    private final Sprite sprite;
    private int iceRemaining;
    private int speedDivisor = 1;
    protected boolean isValid = true;

    protected AbstractFlyingObject(Sprite sprite, int locationX, int locationY, int speedX, int speedY) {
        this.sprite = sprite;
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void forward() {
        locationX += speedX / speedDivisor;
        locationY += speedY / speedDivisor;
        if (iceRemaining > 0) {
            iceRemaining -= Arena.STEP_MS;
            if (iceRemaining <= 0) {
                speedDivisor = 1;
            }
        }
    }

    // 道具时长由对局循环推进。重复冰冻刷新时间，保留原速度供解冻后恢复。
    protected final void freezeFor(int duration, int divisor) {
        iceRemaining = duration;
        speedDivisor = divisor;
    }

    /**
     * 碰撞检测，当对方坐标进入我方范围，判定我方击中<br>
     * 对方与我方覆盖区域有交叉即判定撞击。
     *  <br>
     * 非飞机对象区域：
     *  横向，[x - width/2, x + width/2]
     *  纵向，[y - height/2, y + height/2]
     *  <br>
     * 飞机对象区域：
     *  横向，[x - width/2, x + width/2]
     *  纵向，[y - height/4, y + height/4]
     *
     * @param flyingObject 撞击对方
     * @return true: 我方被击中; false 我方未被击中
     */
    public boolean crash(AbstractFlyingObject flyingObject) {
        // 缩放因子，用于控制 y轴方向区域范围
        int factor = this instanceof AbstractAircraft ? 2 : 1; //我方
        int fFactor = flyingObject instanceof AbstractAircraft ? 2 : 1;//对方

        //对方坐标、宽度、高度
        int x = flyingObject.getLocationX();
        int y = flyingObject.getLocationY();
        int fWidth = flyingObject.getWidth();
        int fHeight = flyingObject.getHeight();

        return x + (fWidth+this.getWidth())/2 > locationX
                && x - (fWidth+this.getWidth())/2 < locationX
                && y + ( fHeight/fFactor+this.getHeight()/factor )/2 > locationY
                && y - ( fHeight/fFactor+this.getHeight()/factor )/2 < locationY;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocation(double locationX, double locationY){
        this.locationX = (int) locationX;
        this.locationY = (int) locationY;
    }

    public int getSpeedY() {
        return speedY / speedDivisor;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getWidth() {
        return sprite.width;
    }

    public int getHeight() {
        return sprite.height;
    }

    /**
     * 标记消失
     * isValid = false.
     * notValid() => true.
     */
    public void vanish() {
        isValid = false;
    }

    public boolean notValid() {
        return !this.isValid;
    }

}
