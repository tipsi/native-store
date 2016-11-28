package com.gettipsi.nativestore.react;

import android.util.Log;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.store.Observer;
import com.gettipsi.nativestore.util.GeneratorRandomeString;

import java.util.HashMap;
import java.util.Map;


public class NativeStoreModule extends ReactContextBaseJavaModule implements GeneratorRandomeString.RandomStringListener, LifecycleEventListener{

    private static final String TAG = NativeStoreModule.class.getSimpleName();
    private static final String MODULE_NAME = "NativeStoreModule";

    private Promise payPromise;

    private NativeStore nativeStoreInstance;
    private Map<String, Observer> observerMap;
    private GeneratorRandomeString generatorRandomeString;


    public NativeStoreModule(ReactApplicationContext reactContext) {
        super(reactContext);
        observerMap = new HashMap<>();
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void init(ReadableMap options) {
        Log.d(TAG, "init: INIT");
//        generatorRandomeString = new GeneratorRandomeString(this);
//        generatorRandomeString.execute();
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
    public void setValue(final String key, final Object value) {
        NativeStore.getInstance().changeData( key, value);
    }

    @Override
    public void onNewString(String s) {
        Log.d(TAG, "onNewString: "+s);
    }

    @Override
    public void onCatalystInstanceDestroy() {
        Log.d(TAG, "onCatalystInstanceDestroy: ");
        super.onCatalystInstanceDestroy();
    }


    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }
}