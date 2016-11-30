package com.gettipsi.nativestore.util;

import com.gettipsi.nativestore.store.Observer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitriy on 11/30/16
 */

public class NativeObserverStub implements Observer {

    private Map<String, Map> currentStateMap;
    private int updateCounter;



    @Override
    public void update(Map<String, HybridMap> soreState) {
        final Map<String , Map> stateMap = new HashMap<>();
        for (String key : soreState.keySet()) {
            final Map<String , Object> copyMap = soreState.get(key).getNativeMap();
            stateMap.put(key, copyMap);
        }
        currentStateMap = stateMap;
        updateCounter++;
    }

    public int getUpdateCounter(){
        return updateCounter;
    }

    public Map<String, Map> getCurrentStateMap(){
        return currentStateMap;
    }
}
