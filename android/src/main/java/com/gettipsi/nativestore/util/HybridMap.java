package com.gettipsi.nativestore.util;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitriy on 11/29/16
 */

public class HybridMap {

  private static final String TAG = HybridMap.class.getSimpleName();

  private HashMap<String, Object> sourceHashMap;
  private ReadableNativeMap sourceReadableMap;
  private WritableNativeMap sourceWritableMap;


  public HybridMap(HashMap<String, Object> sourceHashMap) {
    this.sourceHashMap = sourceHashMap;
  }

  public HybridMap(ReadableNativeMap sourceReadableMap) {
    this.sourceReadableMap = sourceReadableMap;
  }

  public HybridMap(WritableNativeMap sourceWritableMap) {
    this.sourceWritableMap = sourceWritableMap;
  }

  public ReadableMap getReadableMap() {
    if (sourceReadableMap != null) {
      return sourceReadableMap;
    } else if (sourceWritableMap != null) {
      return sourceWritableMap;
    } else {
      sourceWritableMap = (WritableNativeMap) constructWritableMap(sourceHashMap);
      return sourceWritableMap;
    }
  }

  public WritableMap getWritableMap() {
    Log.d(TAG, "getWritableMap: start");
    if (sourceWritableMap != null) {
      Log.d(TAG, "getWritableMap: end");
      return sourceWritableMap;
    } else if (sourceReadableMap != null) {
      sourceWritableMap = (WritableNativeMap) Arguments.createMap();
      sourceWritableMap.merge(sourceReadableMap);
    } else {
      sourceWritableMap = (WritableNativeMap) constructWritableMap(sourceHashMap);
    }
    Log.d(TAG, "getWritableMap: end");
    return sourceWritableMap;
  }

  public WritableMap getWritableMapForReact() {
    Log.d(TAG, "getWritableMap: start");
    final WritableMap copyMap = Arguments.createMap();
    if (sourceWritableMap != null) {
      copyMap.merge(sourceWritableMap);
    } else if(sourceReadableMap != null){
      copyMap.merge(sourceReadableMap);
    } else {
      sourceWritableMap = (WritableNativeMap) constructWritableMap(sourceHashMap);
      copyMap.merge(sourceWritableMap);
    }
    Log.d(TAG, "getWritableMap: end");
    return copyMap;
  }

  public Map<String, Object> getNativeMap() {
    if (sourceHashMap != null) {
      return sourceHashMap;
    } else if (sourceWritableMap != null) {
      sourceHashMap = sourceWritableMap.toHashMap();
    } else if (sourceReadableMap != null) {
      sourceHashMap = sourceReadableMap.toHashMap();
    }
    return sourceHashMap;
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
}
