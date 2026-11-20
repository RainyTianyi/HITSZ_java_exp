package edu.hitsz.android.game.observer;

public class IceActivate extends ItemActivate {
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.beIced();
        }
    }
}
