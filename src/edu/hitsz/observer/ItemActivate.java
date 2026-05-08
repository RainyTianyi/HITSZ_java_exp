package edu.hitsz.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemActivate {
    protected List<Observer> observers;

    public ItemActivate() {
        observers = new ArrayList<>();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    abstract void notifyObservers();
}
