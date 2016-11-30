package com.gettipsi.nativestore.store;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.gettipsi.nativestore.util.HybridMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dmitriy on 11/22/16
 */
public class NativeStore implements Observable {
    private static NativeStore ourInstance;
    private static Map<String, HybridMap> storeMap;
    private List<Observer> observers;


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
        storeMap = new HashMap<>();
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
        changeData(key, ((ReadableNativeMap) value).toHashMap());
    }

    public synchronized void changeData(final String key, final HashMap<String, Object> value) {
        if (storeMap.get(key) == null)
            addItem(key, value);
        else
            updateItem(key, value);
        notifyObservers();
    }

    public void removeData(final String key) {
        storeMap.remove(key);
    }

    public HybridMap getItem(final String key) {
        return storeMap.get(key);
    }

    private void updateItem(final String key, final HashMap<String, Object> value) {
        storeMap.get(key).updateItem(value);
    }

    private void addItem(final String key, final HashMap<String, Object> value) {
        storeMap.put(key, new HybridMap(value));
    }

    public void close() {
        if (ourInstance != null) {
            storeMap.clear();
            storeMap = null;
            observers.clear();
            observers = null;
            ourInstance = null;
        }
    }
}
