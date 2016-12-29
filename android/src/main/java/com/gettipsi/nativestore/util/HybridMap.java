package com.gettipsi.nativestore.util;

import android.util.Log;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitriy on 11/29/16
 */

public class HybridMap {

  private static final String TAG = HybridMap.class.getSimpleName();

  private static final String HYBRID_DATA_FILED_NAME = "mHybridData";

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
    if (sourceReadableMap == null) {
      return sourceWritableMap;
    } else {
      return sourceReadableMap;
    }
  }

  public WritableMap getWritableMap() {
    if (sourceWritableMap == null) {
      Log.d(TAG, "getWritableMap: start");
      sourceWritableMap = toWritableMap(sourceReadableMap);
      Log.d(TAG, "getWritableMap: end");
    }
    return sourceWritableMap;
  }

  public Map<String, Object> getNativeMap() {
    if (sourceHashMap == null)
      sourceHashMap = sourceWritableMap.toHashMap();
    return sourceHashMap;
  }

  private WritableNativeMap toWritableMap(ReadableNativeMap source) {
    final Field field = getHybridDataFiled();
    final HybridData hybridData = getHybridData(source, field);
    return setHybridDataM(hybridData, field);
  }

  private HybridData getHybridData(ReadableNativeMap source, Field field) {
    HybridData hybridData = null;
    if (field != null) {
      field.setAccessible(true);
      try {
        hybridData = (HybridData) field.get((NativeMap) source);
        Log.d(TAG, "getHybridData: hybridData == null : " + (hybridData == null));
      } catch (IllegalAccessException e) {
        Log.e(TAG, "getHybridData: ", e);
      }
    } else {
      Log.d(TAG, "getHybridData: hdf == null");
    }
    return hybridData;
  }

  private WritableNativeMap setHybridDataM(final HybridData hybridData, Field field) {
    final WritableNativeMap writableMap = (WritableNativeMap) Arguments.createMap();
    try {
      field.set((NativeMap) writableMap, hybridData);
    } catch (IllegalAccessException e) {
      Log.e(TAG, "setHybridData: ", e);
    }
    return writableMap;
  }

  private Field getHybridDataFiled() {
    Field field = null;
    try {
      field = NativeMap.class.getDeclaredField(HYBRID_DATA_FILED_NAME);
    } catch (NoSuchFieldException e) {
      Log.e(TAG, "getHybridDataFiled: ", e);
    }
    return field;
  }
}
