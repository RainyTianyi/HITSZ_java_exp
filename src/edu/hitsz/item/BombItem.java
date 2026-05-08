package edu.hitsz.item;

import edu.hitsz.observer.BombActivate;

public class BombItem extends BaseItem{
    private BombActivate bombActivate;

    public BombItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY, 0, 0);
    }

    public void setBombActivate(BombActivate bombActivate) {
        this.bombActivate = bombActivate;
    }

    @Override
    public void activate() { 
        System.out.println("BombItem active!");
        if (bombActivate != null) {
            bombActivate.notifyObservers();
        }
    }
}
