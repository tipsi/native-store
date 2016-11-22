package com.gettipsi.nativestore.react;

import com.gettipsi.nativestore.store.Observer;

/**
 * Created by dmitriy on 11/22/16
 */

public class ReactObserver implements Observer {

    private final String JSObsorverName;

    public ReactObserver(final String JSObsorverName){
        this.JSObsorverName = JSObsorverName;
    }

    @Override
    public void update(float temperature, float humidity, int pressure) {

    }
}
