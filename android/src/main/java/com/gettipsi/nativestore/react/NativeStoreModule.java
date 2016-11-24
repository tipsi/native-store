package com.gettipsi.nativestore.react;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.store.Observer;

import java.util.HashMap;
import java.util.Map;


public class NativeStoreModule extends ReactContextBaseJavaModule {

    private static final String TAG = NativeStoreModule.class.getSimpleName();
    private static final String MODULE_NAME = "NativeStoreModule";

    private Promise payPromise;

    private NativeStore nativeStoreInstance;
    private Map<String, Observer> observerMap;


    public NativeStoreModule(ReactApplicationContext reactContext) {
        super(reactContext);
        observerMap = new HashMap<>();
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void init(ReadableMap options) {
        Log.d(TAG, "init: INIT");
        nativeStoreInstance = NativeStore.getInstance();
    }

    @ReactMethod
    public void subscribe(String functionName, final Promise promise) {
        final ReactObserver observer = new ReactObserver("name");
        observerMap.put("name", observer);
        NativeStore.getInstance().registerObserver(observer);
    }

    @ReactMethod
    public void unsubscribe(ReadableMap data, final Promise promise) {
        final ReactObserver observer = (ReactObserver) observerMap.get("name");
        if(observer != null) {
            observerMap.remove("name");
            NativeStore.getInstance().removeObserver(observer);
        }
    }

    @ReactMethod
    public void setValue(ReadableMap data) {
        NativeStore.getInstance().changeData((float) data.getDouble("temperature"), 0, 0);
    }
}