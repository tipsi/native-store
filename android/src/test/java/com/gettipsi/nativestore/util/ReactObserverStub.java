package com.gettipsi.nativestore.util;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.gettipsi.nativestore.store.Observer;
import com.gettipsi.nativestore.util.HybridMap;

import java.util.Map;

/**
 * Created by dmitriy on 11/30/16
 */

public class ReactObserverStub implements Observer {

    private final String JSObserverName;
    private WritableMap eventData;
    private int eventCounter;

    public ReactObserverStub(final String JSObserverName){
        this.JSObserverName = JSObserverName;
    }


    @Override
    public void update(Map<String, HybridMap> soreState) {
        final WritableMap convertedState = Arguments.createMap();
        for (String key : soreState.keySet()) {
            final WritableMap copyMap = soreState.get(key).getWritableMap();
            convertedState.putMap(key, copyMap);
        }
        sendEvent(JSObserverName, convertedState);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        eventCounter++;
        eventData = params;
    }

    public void getEventsInfo(){

    }
}
