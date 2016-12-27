package com.gettipsi.nativestore.store;

import android.os.Process;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.gettipsi.nativestore.util.HybridMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
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
    final Runnable task = new Runnable() {
      public void run() {
        Log.d(TAG, "run: start Thread");
        state = new HybridMap((ReadableNativeMap) value);
        notifyObservers();
        Log.d(TAG, "run: end Thread");
      }
    };

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
//    new Thread(new Runnable() {
//      public void run() {
//        Log.d(TAG, "run: start Thread");
////        int tid= Process.myTid();
////        Log.d(TAG,"priority before change = " + android.os.Process.getThreadPriority(tid));
////        Log.d(TAG,"priority before change = "+Thread.currentThread().getPriority());
////        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_LOWEST);
////        Log.d(TAG,"priority after change = " + android.os.Process.getThreadPriority(tid));
////        Log.d(TAG,"priority after change = " + Thread.currentThread().getPriority());
//        setState(((ReadableNativeMap) value).toHashMap());
//        Log.d(TAG, "run: end Thread");
//      }
//    }).start();
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
