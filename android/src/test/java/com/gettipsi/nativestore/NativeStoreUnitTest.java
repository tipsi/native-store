package com.gettipsi.nativestore;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.store.Observer;
import com.gettipsi.nativestore.util.NativeObserverStub;
import com.gettipsi.nativestore.util.ReactObserverStub;
import com.gettipsi.nativestore.util.ThreadListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
@PrepareForTest({Arguments.class, WritableNativeMap.class, Log.class})
public class NativeStoreUnitTest {

  private final String TAG = getClass().getSimpleName();
  @Rule
  public final PowerMockRule rule = new PowerMockRule();

  private static final String REACT_OBSERVER_NAME = "REACT_OBSERVER_NAME";
  private static final String TEST_KEY = "test_key";
  private static final String TEST_VALUE = "test_value";
  private NativeStore nativeStore;

  private volatile boolean condition;

  //Without this lines I have the same problem:
  // http://stackoverflow.com/questions/35275772/unsatisfiedlinkerror-when-unit-testing-writablenativemap
  @Before
  public void setUp() {
    System.out.println("Before");
    PowerMockito.mockStatic(Arguments.class);
    PowerMockito.when(Arguments.createArray()).thenAnswer(new Answer<Object>() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        return new JavaOnlyArray();
      }
    });
    PowerMockito.when(Arguments.createMap()).thenAnswer(new Answer<Object>() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        return new JavaOnlyMap();
      }
    });
    nativeStore = NativeStore.getInstance();
    PowerMockito.mockStatic(Log.class);
  }

  @After
  public void clearData() {
    System.out.println("After");
    NativeStore.getInstance().close();
  }

  @Test
  public void instanceCreationTest() throws Exception {
    assertNotNull("Store instance didn't create", NativeStore.getInstance());
  }

  @Test
  public void setStateTest() throws Exception {
    final int[] count = {0};
//        final Map testMap = setValue(TEST_KEY, TEST_VALUE);
    final HashMap<String, Object> testMap = new HashMap<>();
    testMap.put(TEST_KEY, TEST_VALUE);
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count[0]++;
        assertEquals("Object didn't added to store", nativeStore.getState().getNativeMap(), testMap);
        condition = true;
        System.out.println("setStateTest endTask "+count[0]);
      }
    });
    nativeStore.setState(testMap);

    while (count[0] < 1){
      System.out.println("setStateTest "+count[0]);
      Thread.yield();
//            try {
//                Thread.sleep(500);
//                System.out.println("setStateTest without InterruptedException");
//            } catch (InterruptedException e) {
//                System.out.println("setStateTest InterruptedException");
//            }
    }
    condition = false;
//    final Map testMap = setValue(TEST_KEY, TEST_VALUE);
//    Thread.sleep(500);
//    final Map mapFromStore = nativeStore.getState().getNativeMap();

//    assertEquals("Object didn't added to store", mapFromStore, testMap);
  }

  @Test
  public void subscribeReactTest() throws Exception {
    final int[] count = {0};
    final ReactObserverStub observer = subscribeReact(REACT_OBSERVER_NAME);
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count[0]++;
        if(count[0] >= 3) {
          assertTrue("Observer didn't send 3 events, or currentStateMap == null, or currentStateMap no instanceof WritableMap",
            observer.getEventCounter() == 3
              && observer.getCurrentStateMap() != null);
          condition = true;
        }
      }
    });
    changeStoreStateThreeTimes();

    while (count[0] < 3){
      System.out.println("subscribeReactTest tttttttteeeeeessssstttt");
      Thread.yield();
//            try {
//                Thread.sleep(500);
//                System.out.println("subscribeReactTest without InterruptedException");
//            } catch (InterruptedException e) {
//                System.out.println("subscribeReactTest InterruptedException");
//            }
    }

    condition = false;
//    Thread.sleep(500);
//
//    assertTrue("Observer didn't send 3 events, or currentStateMap == null, or currentStateMap no instanceof WritableMap",
//      observer.getEventCounter() == 3
//        && observer.getCurrentStateMap() != null);
  }

  @Test
  public void unsubscribeReactTest() throws Exception {
    final int[] count = {0};
    final ReactObserverStub observer = subscribeReact(REACT_OBSERVER_NAME);
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count[0]++;
        if(count[0] >= 3) {
          assertEquals("The observer was not unsubscribe", observer.getEventCounter(), 0);
          condition = true;
        }
      }
    });
    unsubscribe(observer);
    changeStoreStateThreeTimes();

    while (count[0] < 3){
      System.out.println("unsubscribeReactTest tttttttteeeeeessssstttt");
      Thread.yield();
//            try {
//                Thread.sleep(500);
//                System.out.println("unsubscribeReactTest without InterruptedException");
//            } catch (InterruptedException e) {
//                System.out.println("unsubscribeReactTest InterruptedException");
//            }
    }
    condition = false;

//        Thread.sleep(500);
//
//        assertEquals("The observer was not unsubscribe", observer.getEventCounter(), 0);
  }

  @Test
  public void subscribeNativeTest() throws Exception {
    final int[] count = {0};
    final NativeObserverStub observer = subscribeNative();
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count[0]++;
        if(count[0] >= 3) {
          System.out.println("subscribeNativeTest "+observer.getUpdateCounter());
          System.out.println("subscribeNativeTest "+(observer.getCurrentStateMap() != null));
          System.out.println("subscribeNativeTest "+(observer.getCurrentStateMap() instanceof Map));
          assertTrue("Observer didn't receive 3 updates, or currentStateMap == null, or currentStateMap no instanceof Map",
            observer.getUpdateCounter() == 3
              && observer.getCurrentStateMap() != null
              && observer.getCurrentStateMap() instanceof Map);
          condition = true;
        }
      }
    });
    changeStoreStateThreeTimes();

    while (count[0] < 3){
      System.out.println("subscribeNativeTest tttttttteeeeeessssstttt");
      Thread.yield();
//            try {
//                Thread.sleep(500);
//                System.out.println("subscribeNativeTest without InterruptedException");
//            } catch (InterruptedException e) {
//                System.out.println("subscribeNativeTest InterruptedException");
//            }
    }
    condition = false;
    Log.d(TAG, "subscribeNativeTest: end");
//        Thread.sleep(500);
//
//        assertTrue("Observer didn't receive 3 updates, or currentStateMap == null, or currentStateMap no instanceof Map",
//                observer.getUpdateCounter() == 3
//                        && observer.getCurrentStateMap() != null
//                        && observer.getCurrentStateMap() instanceof Map);
  }

  @Test
  public void unsubscribeNativeTest() throws Exception {
    final int[] count = {0};
    final NativeObserverStub observer = subscribeNative();
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count[0]++;
        assertEquals("The observer was not unsubscribe", observer.getUpdateCounter(), 0);
        condition = true;
      }
    });
    unsubscribe(observer);
    changeStoreStateThreeTimes();

    while (count[0] < 3){
      System.out.println("unsubscribeNativeTest tttttttteeeeeessssstttt");
      Thread.yield();
//            try {
////                Thread.yield();
//                Thread.sleep(500);
//                System.out.println("unsubscribeNativeTest without InterruptedException");
//            } catch (InterruptedException e) {
//                System.out.println("unsubscribeNativeTest InterruptedException");
//            }
    }
    condition = false;

//        Thread.sleep(500);
//
//        assertEquals("The observer was not unsubscribe", observer.getUpdateCounter(), 0);
  }

  private Map<String, Object> setValue(final String key, final String value) {
    final HashMap<String, Object> testMap = new HashMap<>();
    testMap.put(key, value);
    nativeStore.setState(testMap);
    return testMap;
  }


  private ReactObserverStub subscribeReact(final String observerName) {
    final ReactObserverStub observerStub = new ReactObserverStub(observerName);
    nativeStore.subscribe(observerStub);
    return observerStub;
  }

  private NativeObserverStub subscribeNative() {
    final NativeObserverStub observerStub = new NativeObserverStub();
    nativeStore.subscribe(observerStub);
    return observerStub;
  }

  private void unsubscribe(final Observer observer) {
    nativeStore.unsubscribe(observer);
  }

  private void changeStoreStateThreeTimes() {
    for (int i = 0; i < 3; i++) {
      setValue(TEST_KEY + i, TEST_VALUE + i);
    }
  }
}
