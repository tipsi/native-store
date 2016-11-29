package com.gettipsi.nativestore.view;


import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.store.Observer;

import java.util.Map;

/**
 * Created by dmitriy on 11/22/16
 */

public class CurrentDataDisplay implements Observer {

    private static final String TAG = CurrentDataDisplay.class.getSimpleName();
    private final NativeStore store;

    public CurrentDataDisplay(final NativeStore store){
        this.store = store;
        store.registerObserver(this);
    }

    @Override
    public void update(Map<String, ReadableMap> soreState) {
        Log.d(TAG, "update: NEW VALUE == "+soreState);
    }
}
