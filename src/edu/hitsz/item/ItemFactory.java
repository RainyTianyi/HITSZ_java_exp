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
     * @param speedX X 方向速度
     * @param speedY Y 方向速度
     * @param value 道具值（血量或持续时间）
     * @return 创建的道具对象
     */
    public static BaseItem createItem(String type, int locationX, int locationY,
                                      int speedX, int speedY, int value) {
        BaseItem item = null;

        switch (type) {
            case TYPE_BLOOD:
                item = new BloodItem(locationX, locationY, speedX, speedY, value);
                break;
            case TYPE_FIRE:
                item = new FireItem(locationX, locationY, speedX, speedY, value);
                break;
            case TYPE_FIRE_PLUS:
                item = new FirePlusItem(locationX, locationY, speedX, speedY, value);
                break;
            case TYPE_BOMB:
                item = new BombItem(locationX, locationY, speedX, speedY, value);
                break;
            case TYPE_ICE:
                item = new IceItem(locationX, locationY, speedX, speedY, value);
                break;
            default:
                throw new IllegalArgumentException("Unknown item type: " + type);
        }

        return item;
    }
}
