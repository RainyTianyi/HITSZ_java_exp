package edu.hitsz.android.game.item;

import edu.hitsz.android.game.Sprite;
import edu.hitsz.android.game.aircraft.HeroAircraft;

import edu.hitsz.android.game.observer.IceActivate;

public class IceItem extends BaseItem{
    private IceActivate iceActivate;

    public IceItem(int locationX, int locationY, int speedX, int speedY) {
        super(Sprite.ICE, locationX, locationY, speedX, speedY, 0, 0);
    }

    public void setIceActivate(IceActivate iceActivate) {
        this.iceActivate = iceActivate;
    }

    @Override
    public void activate(HeroAircraft hero) {
        if (iceActivate != null) {
            iceActivate.notifyObservers();
        }
    }
}
