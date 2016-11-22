package com.gettipsi.nativestore.store;

/**
 * Created by dmitriy on 11/22/16
 */

public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
