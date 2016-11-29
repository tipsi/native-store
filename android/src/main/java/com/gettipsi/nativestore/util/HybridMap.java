package com.gettipsi.nativestore.util;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitriy on 11/29/16
 */

public class HybridMap {

    private final HashMap<String, Object> sourceHashMap;


    public HybridMap(HashMap<String, Object> sourceHashMap) {
        this.sourceHashMap = sourceHashMap;
    }

    public WritableMap getWritableMap() {
        return constructWritableMap(sourceHashMap);
    }

    private WritableMap constructWritableMap(final HashMap<String, Object> source) {
        final WritableMap writableMap = Arguments.createMap();
        for (String key : source.keySet()) {
            final Object value = source.get(key);
            if (value == null) {
                writableMap.putNull(key);
            } else if (value instanceof Boolean) {
                writableMap.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                writableMap.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                writableMap.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                writableMap.putString(key, (String) value);
            } else if (value instanceof Map) {
                writableMap.putMap(key, (constructWritableMap((HashMap<String, Object>) value)));
            } else if (value instanceof ArrayList) {
                writableMap.putArray(key, constructWritableArray((ArrayList<Object>) value));
            }
        }
        return writableMap;
    }

    private WritableArray constructWritableArray(ArrayList<Object> arrayList) {
        final WritableArray writableArray = Arguments.createArray();
        for (Object value : arrayList) {
            if (value == null) {
                writableArray.pushNull();
            } else if (value instanceof Boolean) {
                writableArray.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                writableArray.pushInt((Integer) value);
            } else if (value instanceof Double) {
                writableArray.pushDouble((Double) value);
            } else if (value instanceof String) {
                writableArray.pushString((String) value);
            } else if (value instanceof Map) {
                writableArray.pushMap((constructWritableMap((HashMap<String, Object>) value)));
            } else if (value instanceof ArrayList) {
                writableArray.pushArray(constructWritableArray((ArrayList<Object>) value));
            }
        }
        return writableArray;
    }

    public void updateItem(HashMap<String, Object> value) {
        sourceHashMap.putAll(value);
    }
}
