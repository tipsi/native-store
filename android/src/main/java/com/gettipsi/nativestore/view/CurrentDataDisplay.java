package com.gettipsi.nativestore.view;


import android.util.Log;

import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.store.Observer;

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
    public void update(float temperature, float humidity, int pressure) {
        Log.d(TAG, "update: NEW VALUE == "+temperature);
    }
}
