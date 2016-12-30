package com.gettipsi.nativestore.store;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.gettipsi.nativestore.react.ReactObserver;
import com.gettipsi.nativestore.util.HybridMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by dmitriy on 11/22/16
 */
public class NativeStore implements Observable {

  private static final String TAG = NativeStore.class.getSimpleName();

  private static NativeStore ourInstance;
  private static HybridMap state;
  private static List<Observer> observers;
  private static ThreadPoolExecutor poolExecutor;


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
    poolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    poolExecutor.setMaximumPoolSize(1);
  }

  @Override
  public void subscribe(Observer o) {
    if (o instanceof ReactObserver)
      observers.add(0, o);
    else
      observers.add(o);
  }

  @Override
  public void unsubscribe(Observer o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update(state);
    }
  }

  public void setState(final HashMap<String, Object> value) {
    final Runnable task = new Runnable() {
      public void run() {
        state = new HybridMap(value);
        notifyObservers();
      }
    };
    setState(task);
  }

  public void setState(final ReadableMap value) {
    final Runnable task = new Runnable() {
      public void run() {
        state = new HybridMap((ReadableNativeMap) value);
        notifyObservers();
      }
    };
    setState(task);
  }

  public void setState(final WritableMap value) {
    final Runnable task = new Runnable() {
      public void run() {
        state = new HybridMap((WritableNativeMap) value);
        notifyObservers();
      }
    };
    setState(task);
  }

  private void setState(final Runnable task) {
    try {
      poolExecutor.execute(task);
    } catch (RejectedExecutionException e) {
      Log.e(TAG, "setState: ", e);
      try {
        poolExecutor.getQueue().put(task);
      } catch (InterruptedException e1) {
        Log.e(TAG, "setState: ", e1);
      }
    }
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
      poolExecutor.shutdown();
      poolExecutor = null;
    }
  }
}
