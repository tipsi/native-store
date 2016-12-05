package com.gettipsi.nativestore.store;

/**
 * Created by dmitriy on 11/22/16
 */

public interface Observable {
    void subscribe(Observer o);
    void unsubscribe(Observer o);
    void notifyObservers();
}
