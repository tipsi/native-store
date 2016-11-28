package com.gettipsi.nativestore.store;

import com.facebook.react.bridge.WritableMap;

/**
 * Created by dmitriy on 11/22/16
 */

public interface Observer {
    void update(WritableMap soreState);
}