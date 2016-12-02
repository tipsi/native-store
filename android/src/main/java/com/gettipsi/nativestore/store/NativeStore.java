package com.gettipsi.nativestore.store;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.gettipsi.nativestore.util.HybridMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dmitriy on 11/22/16
 */
public class NativeStore implements Observable {
    private static NativeStore ourInstance;
    private static HybridMap state;
    private static List<Observer> observers;


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
//        storeMap = new HashMap<>();
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
            observer.update(state);
    }


    public synchronized void setState(final HashMap<String, Object> value) {
        if (state == null)
            state = new HybridMap(value);
        else
            state.updateItem(value);
        notifyObservers();
    }

    public void close() {
        if (ourInstance != null) {
            state = null;
            observers.clear();
            observers = null;
            ourInstance = null;
        }
    }

    public void setState(final ReadableMap value) {
        setState(((ReadableNativeMap) value).toHashMap());
    }

    public HybridMap getState() {
        return null;
    }
}
