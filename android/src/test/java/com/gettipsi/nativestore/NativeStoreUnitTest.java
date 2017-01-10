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
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
@PrepareForTest({Arguments.class, WritableNativeMap.class, Log.class})
public class NativeStoreUnitTest {

  @Rule
  public final PowerMockRule rule = new PowerMockRule();

  private static final String REACT_OBSERVER_NAME = "REACT_OBSERVER_NAME";
  private static final String TEST_KEY = "test_key";
  private static final String TEST_VALUE = "test_value";
  private NativeStore nativeStore;


  //Without this lines I have the same problem:
  // http://stackoverflow.com/questions/35275772/unsatisfiedlinkerror-when-unit-testing-writablenativemap
  @Before
  public void setUp() {
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
    NativeStore.getInstance().close();
  }

  @Test
  public void instanceCreationTest() throws Exception {
    assertNotNull("Store instance didn't create", NativeStore.getInstance());
  }

  @Test
  public void setStateTest() throws Exception {
    final AtomicInteger atomicInteger = new AtomicInteger(0);
    final HashMap<String, Object> testMap = new HashMap<>();
    testMap.put(TEST_KEY, TEST_VALUE);
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        atomicInteger.incrementAndGet();

        assertEquals("Object didn't added to store", nativeStore.getState().getNativeMap(), testMap);
      }
    });
    nativeStore.setState(testMap);

    waitUntilTaskEnd(atomicInteger, 1);
  }

  @Test
  public void subscribeReactTest() throws Exception {
    final AtomicInteger count = new AtomicInteger(0);
    final ReactObserverStub observer = subscribeReact(REACT_OBSERVER_NAME);
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count.incrementAndGet();
        if (count.get() >= 3) {

          assertTrue("Observer didn't send 3 events, or currentStateMap == null, or currentStateMap no instanceof WritableMap",
            observer.getEventCounter() == 3
              && observer.getCurrentStateMap() != null);
        }
      }
    });
    changeStoreStateThreeTimes();

    waitUntilTaskEnd(count, 3);
  }

  @Test
  public void unsubscribeReactTest() throws Exception {
    final AtomicInteger count = new AtomicInteger(0);
    final ReactObserverStub observer = subscribeReact(REACT_OBSERVER_NAME);
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count.incrementAndGet();
        if (count.get() >= 3) {

          assertEquals("The observer was not unsubscribe", observer.getEventCounter(), 0);
        }
      }
    });
    unsubscribe(observer);
    changeStoreStateThreeTimes();

    waitUntilTaskEnd(count, 3);
  }

  @Test
  public void subscribeNativeTest() throws Exception {
    final AtomicInteger count = new AtomicInteger(0);
    final NativeObserverStub observer = subscribeNative();
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count.incrementAndGet();
        if (count.get() >= 3) {

          assertTrue("Observer didn't receive 3 updates, or currentStateMap == null, or currentStateMap no instanceof Map",
            observer.getUpdateCounter() == 3
              && observer.getCurrentStateMap() != null
              && observer.getCurrentStateMap() instanceof Map);
        }
      }
    });
    changeStoreStateThreeTimes();

    waitUntilTaskEnd(count, 3);
  }

  @Test
  public void unsubscribeNativeTest() throws Exception {
    final AtomicInteger count = new AtomicInteger(0);
    final NativeObserverStub observer = subscribeNative();
    nativeStore.setListener(new ThreadListener() {
      @Override
      public void endTask() {
        count.incrementAndGet();

        assertEquals("The observer was not unsubscribe", observer.getUpdateCounter(), 0);
      }
    });
    unsubscribe(observer);
    changeStoreStateThreeTimes();

    waitUntilTaskEnd(count, 3);
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

  private void waitUntilTaskEnd(final AtomicInteger count, final int conditionCount){
    while (count.get() < conditionCount) {
      Thread.yield();
    }
  }
}
