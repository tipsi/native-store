package com.gettipsi.nativestore.store;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 11/22/16
 */
public class NativeStore implements Observable {
    private static NativeStore ourInstance = new NativeStore();
    private static WritableMap storeMap;
    private List<Observer> observers;
    private boolean item;


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
        storeMap = Arguments.createMap();
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
            observer.update(storeMap);
    }

    public void changeData(final String key, final ReadableMap value) {
        WritableMap sourceMap = Arguments.createMap();
        sourceMap.merge(value);
        storeMap.putMap(key, sourceMap);
        notifyObservers();
    }

    public void removeData(final String key) {
        storeMap.putNull(key);
    }

    public WritableMap getItem(final String key) {
        if(storeMap.isNull(key))
            return null;
        else {
            final WritableMap item = Arguments.createMap();
            item.merge(storeMap.getMap(key));
            return item;
        }
    }
}
