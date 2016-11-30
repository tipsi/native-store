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
import com.gettipsi.nativestore.util.HybridMap;

import java.util.HashMap;
import java.util.Map;


public class NativeStoreModule extends ReactContextBaseJavaModule implements GeneratorRandomeString.RandomStringListener, LifecycleEventListener {

    private static final String TAG = NativeStoreModule.class.getSimpleName();
    private static final String MODULE_NAME = "NativeStoreModule";
    private final ReactApplicationContext reactContext;

    private Map<String, Observer> observerMap;
    private GeneratorRandomeString generatorRandomeString;


    public NativeStoreModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        observerMap = new HashMap<>();
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }


    @ReactMethod
    public void subscribe(final String reactObserverName) {
        Log.d(TAG, "subscribe: ");
        final ReactObserver observer = new ReactObserver(reactObserverName, reactContext);
        observerMap.put(reactObserverName, observer);
        NativeStore.getInstance().registerObserver(observer);
    }

    @ReactMethod
    public void unsubscribe(final String reactObserverName) {
        Log.d(TAG, "unsubscribe: ");
        final ReactObserver observer = (ReactObserver) observerMap.get(reactObserverName);
        if (observer != null) {
            NativeStore.getInstance().removeObserver(observer);
            observerMap.remove(reactObserverName);
        }
    }

    @ReactMethod
    public void setValue(final String key, final ReadableMap value) {
        Log.d(TAG, "setValue: " + value.getClass());
        NativeStore.getInstance().changeData(key, value);
    }

    @ReactMethod
    public void getValue(final String key, final Promise promise) {
        Log.d(TAG, "getValue: ");
        final HybridMap item = NativeStore.getInstance().getItem(key);
        if (item == null) {
            promise.reject(TAG, "No value for key \"" + key + "\"");
        } else {
            promise.resolve(item.getWritableMap());
        }
    }

    @ReactMethod
    public void removeValue(final String key) {
        Log.d(TAG, "removeValue: ");
        NativeStore.getInstance().removeData(key);
    }

    @Override
    public void onNewString(String s) {
        Log.d(TAG, "onNewString: " + s);
        final HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", s);
        NativeStore.getInstance().changeData("main_state", map);
    }


    @Override
    public void onHostResume() {
        Log.d(TAG, "onHostResume: ");
        generatorRandomeString = new GeneratorRandomeString(this);
        generatorRandomeString.execute();
    }

    @Override
    public void onHostPause() {
        generatorRandomeString.close();
    }

    @Override
    public void onHostDestroy() {
        NativeStore.getInstance().close();
    }
}