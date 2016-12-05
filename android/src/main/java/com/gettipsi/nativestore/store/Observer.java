package com.gettipsi.nativestore.store;

import com.gettipsi.nativestore.util.HybridMap;

import java.util.Map;

/**
 * Created by dmitriy on 11/22/16
 */

public interface Observer {
    void update(HybridMap storeState);
}