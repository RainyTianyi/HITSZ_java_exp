package edu.hitsz.observer;

public class BombActivate extends ItemActivate{
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.beBombed();
        }
    }
}
