package edu.hitsz.item;

import edu.hitsz.observer.IceActivate;

public class IceItem extends BaseItem{
    private IceActivate iceActivate;

    public IceItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY, 0, 0);
    }

    public void setIceActivate(IceActivate iceActivate) {
        this.iceActivate = iceActivate;
    }

    @Override
    public void activate() {
        System.out.println("IceItem active!");
        if (iceActivate != null) {
            iceActivate.notifyObservers();
        }
    }
}
