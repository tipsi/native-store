package com.gettipsi.nativestore.util;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.WritableMap;
import com.gettipsi.nativestore.store.Observer;

import java.util.Map;

/**
 * Created by dmitriy on 11/30/16
 */

public class ReactObserverStub implements Observer {

    private final String JSObserverName;
    private WritableMap currentStateMap;
    private int eventCounter;

    public ReactObserverStub(final String JSObserverName){
        this.JSObserverName = JSObserverName;
    }


    @Override
    public void update(HybridMap storeState) {
        sendEvent(JSObserverName, storeState.getWritableMap());
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        eventCounter++;
        currentStateMap = params;
    }

    public int getEventCounter(){
        return eventCounter;
    }

    public WritableMap getCurrentStateMap(){
        return currentStateMap;
    }
}
