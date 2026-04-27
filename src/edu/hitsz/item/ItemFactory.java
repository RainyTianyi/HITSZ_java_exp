package edu.hitsz.item;

public class ItemFactory {

    // 道具类型常量
    public static final String TYPE_BLOOD = "Blood";
    public static final String TYPE_FIRE = "Fire";
    public static final String TYPE_FIRE_PLUS = "FirePlus";
    public static final String TYPE_BOMB = "Bomb";
    public static final String TYPE_ICE = "Ice";

    /**
     * 简单工厂方法：根据类型创建道具
     * @param type 道具类型
     * @param locationX X 坐标
     * @param locationY Y 坐标
     * @return 创建的道具对象
     */
    public static BaseItem createItem(String type, int locationX, int locationY) {
        BaseItem item = null;
        int value, duration;
        int speedX, speedY;

        // 先统一指定道具的速度
        // TODO 不同道具的速度不同
        speedX = 0;
        speedY = 10;

        // 生成各种道具，并制定道具相关的值
        switch (type) {
            case TYPE_BLOOD:
                value = 50; // 回血量大小
                item = new BloodItem(locationX, locationY, speedX, speedY, value);
                break;
            case TYPE_FIRE:
                duration = 5000;  // 持续时间
                item = new FireItem(locationX, locationY, speedX, speedY, duration);
                break;
            case TYPE_FIRE_PLUS:
                duration = 10000; // 持续时间
                item = new FirePlusItem(locationX, locationY, speedX, speedY, duration);
                break;
            case TYPE_BOMB:
                value = 100;    // 炸弹伤害
                item = new BombItem(locationX, locationY, speedX, speedY, value);
                break;
            case TYPE_ICE:
                duration = 100; // 持续时间
                item = new IceItem(locationX, locationY, speedX, speedY, duration);
                break;
            default:
                throw new IllegalArgumentException("Unknown item type: " + type);
        }

        return item;
    }
}
