package com.gettipsi.nativestore.react;

import android.util.Log;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.util.HybridMap;


public class NativeStoreModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private static final String TAG = NativeStoreModule.class.getSimpleName();
    private static final String MODULE_NAME = "NativeStoreModule";
    private final ReactApplicationContext reactContext;
    private ReactObserver reactObserver;


    public NativeStoreModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void setState(final ReadableMap value) {
        Log.d(TAG, "setState: " + value.getClass());
        registerReactObserver();
        NativeStore.getInstance().setState(value);
    }

    @ReactMethod
    public void getState(final Promise promise) {
        Log.d(TAG, "getState: ");
        final HybridMap item = NativeStore.getInstance().getState();
        if (item == null) {
            promise.reject(TAG, "State is empty");
        } else {
            promise.resolve(item.getWritableMap());
        }
    }

    private void registerReactObserver(){
        if(reactObserver == null){
            reactObserver = new ReactObserver(reactContext);
            NativeStore.getInstance().registerObserver(reactObserver);
        }
    }

    @Override
    public void onHostResume() {
        Log.d(TAG, "onHostResume: ");
        registerReactObserver();
    }

    @Override
    public void onHostPause() {
    }

    @Override
    public void onHostDestroy() {
        NativeStore.getInstance().close();
        reactObserver = null;
    }
}
