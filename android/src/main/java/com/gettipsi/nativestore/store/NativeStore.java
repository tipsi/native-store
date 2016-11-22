package com.gettipsi.nativestore.store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 11/22/16
 */
public class NativeStore implements Observable {
    private static NativeStore ourInstance = new NativeStore();
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private int pressure;

    public static NativeStore getInstance() {
        if (ourInstance == null)
            init();
        return ourInstance;
    }

    private NativeStore() {
        observers = new ArrayList<>();
    }

    private static void init() {
        ourInstance = new NativeStore();
    }


    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(temperature, humidity, pressure);
    }

    public void changeData(final float temperature, final float humidity, final int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}
