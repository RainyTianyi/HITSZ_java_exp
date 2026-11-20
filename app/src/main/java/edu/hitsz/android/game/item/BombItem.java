package edu.hitsz.android.game.item;

import edu.hitsz.android.game.Sprite;
import edu.hitsz.android.game.aircraft.HeroAircraft;

import edu.hitsz.android.game.observer.BombActivate;

public class BombItem extends BaseItem{
    private BombActivate bombActivate;

    public BombItem(int locationX, int locationY, int speedX, int speedY) {
        super(Sprite.BOMB, locationX, locationY, speedX, speedY, 0, 0);
    }

    public void setBombActivate(BombActivate bombActivate) {
        this.bombActivate = bombActivate;
    }

    @Override
    public void activate(HeroAircraft hero) {
        if (bombActivate != null) {
            bombActivate.notifyObservers();
        }
    }
}
