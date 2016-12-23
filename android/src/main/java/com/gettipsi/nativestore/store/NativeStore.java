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
  }

  @Override
  public void subscribe(Observer o) {
    observers.add(o);
  }

  @Override
  public void unsubscribe(Observer o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers)
      observer.update(state);
  }

  public synchronized void setState(final HashMap<String, Object> value) {
    state = new HybridMap(value);
    notifyObservers();
  }

  public void setState(final ReadableMap value) {
    new Thread(new Runnable() {
      public void run() {
        setState(((ReadableNativeMap) value).toHashMap());
      }
    }).start();
  }

  public HybridMap getState() {
    return state;
  }

  public void close() {
    if (ourInstance != null) {
      state = null;
      observers.clear();
      observers = null;
      ourInstance = null;
    }
  }
}
