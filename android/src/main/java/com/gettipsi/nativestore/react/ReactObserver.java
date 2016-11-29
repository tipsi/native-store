package com.gettipsi.nativestore.react;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.gettipsi.nativestore.store.Observer;
import com.gettipsi.nativestore.util.HybridMap;

import java.util.Map;

/**
 * Created by dmitriy on 11/22/16
 */

public class ReactObserver implements Observer {

    private final String JSObserverName;
    private final ReactContext reactContext;

    public ReactObserver(final String JSObserverName, ReactContext reactContext){
        this.JSObserverName = JSObserverName;
        this.reactContext = reactContext;
    }


    @Override
    public void update(Map<String, HybridMap> soreState) {
        final WritableArray convertedState = Arguments.createArray();
        for (String s : soreState.keySet()) {
            final WritableMap copyMap = soreState.get(s).getWritableMap();
            convertedState.pushMap(copyMap);
        }
        sendEvent(reactContext, JSObserverName, convertedState);
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableArray params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
