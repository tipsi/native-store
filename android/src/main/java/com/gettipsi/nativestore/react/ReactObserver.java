package com.gettipsi.nativestore.react;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.gettipsi.nativestore.store.Observer;

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
    public void update(WritableMap soreState) {
        sendEvent(reactContext, JSObserverName, soreState);
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
