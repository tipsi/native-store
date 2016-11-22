package com.gettipsi.nativestore.store;

/**
 * Created by dmitriy on 11/22/16
 */

public interface Observer {
    void update(float temperature, float humidity, int pressure);
}
