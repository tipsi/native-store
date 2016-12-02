package com.gettipsi.nativestore.react;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.gettipsi.nativestore.store.Observer;
import com.gettipsi.nativestore.util.HybridMap;

/**
 * Created by dmitriy on 11/22/16
 */

public class ReactObserver implements Observer {

    private static final String JS_OBSERVER_NAME = "storage:change";
    private final ReactContext reactContext;

    public ReactObserver(ReactContext reactContext) {
        this.reactContext = reactContext;
    }


    @Override
    public void update(HybridMap soreState) {
        sendEvent(reactContext, JS_OBSERVER_NAME, soreState.getWritableMap());
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
