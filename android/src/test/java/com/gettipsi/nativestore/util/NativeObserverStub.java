package com.gettipsi.nativestore.util;

import com.gettipsi.nativestore.store.Observer;

import java.util.Map;

/**
 * Created by dmitriy on 11/30/16
 */

public class NativeObserverStub implements Observer {

  private Map<String, Object> currentStateMap;
  private int updateCounter;



  @Override
  public void update(HybridMap storeState) {
    currentStateMap = storeState.getNativeMap();
    updateCounter++;
  }

  public int getUpdateCounter(){
    return updateCounter;
  }

  public Map<String, Object> getCurrentStateMap(){
    return currentStateMap;
  }
}
