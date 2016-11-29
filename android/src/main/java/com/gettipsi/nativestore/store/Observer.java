package com.gettipsi.nativestore.store;

import com.facebook.react.bridge.ReadableMap;
import java.util.Map;

/**
 * Created by dmitriy on 11/22/16
 */

public interface Observer {
    void update(Map<String, ReadableMap> soreState);
}