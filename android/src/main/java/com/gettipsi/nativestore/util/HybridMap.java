package com.gettipsi.nativestore.util;

import android.util.Log;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.lang.reflect.Field;
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
//        hakc(sourceReadableMap);
  }


  public ReadableMap getReadableMap() {
    return sourceReadableMap;
  }

  public WritableMap getWritableMap() {
//        return constructWritableMap(sourceHashMap);
    if (sourceWritableMap == null) {
      Log.d(TAG, "getWritableMap: start");
//            sourceWritableMap = (WritableNativeMap) Arguments.createMap();
//            sourceWritableMap.merge(sourceReadableMap);
      sourceWritableMap = hakc(sourceReadableMap);
      Log.d(TAG, "getWritableMap: end");
    }
    return sourceWritableMap;
  }

  public Map<String, Object> getNativeMap() {
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

  private WritableNativeMap hakc(ReadableNativeMap source) {
    Field hdf = null;
    try {
      hdf = NativeMap.class.getDeclaredField("mHybridData");
    } catch (NoSuchFieldException e) {
      Log.e(TAG, "hakc: ", e);
    }

    if(hdf != null){
      hdf.setAccessible(true);
      try {
        HybridData hybridData = (HybridData) hdf.get((NativeMap)source);
        Log.d(TAG, "hakc: hybridData == null : "+ (hybridData == null));
        Log.d(TAG, "hakc: hybridData.getClass().getSimpleName() "+hybridData.getClass().getSimpleName());
        return hack2(hybridData, hdf);
      } catch (IllegalAccessException e) {
        Log.e(TAG, "hakc: ", e);
      }
    } else {
      Log.d(TAG, "hakc: hdf == null");
    }
    return null;
  }

  private WritableNativeMap hack2(final HybridData hybridData, Field hdf){
    final WritableNativeMap writableMap = (WritableNativeMap) Arguments.createMap();
//        writableMap
    try {
      hdf.set((NativeMap)writableMap, hybridData);
      Log.d(TAG, "hack2: "+writableMap.keySetIterator().hasNextKey());
      Log.d(TAG, "hack2: "+writableMap.keySetIterator().nextKey());
      return writableMap;
    } catch (IllegalAccessException e) {
      Log.e(TAG, "hack2: ", e);
    }
    return null;
  }
}
