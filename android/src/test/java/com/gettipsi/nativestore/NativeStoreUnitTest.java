package com.gettipsi.nativestore;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.WritableMap;
import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.store.Observer;
import com.gettipsi.nativestore.util.NativeObserverStub;
import com.gettipsi.nativestore.util.ReactObserverStub;

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

@RunWith(JUnit4.class)
@PrepareForTest({Arguments.class})
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
        final Map testMap = setValue(TEST_KEY, TEST_VALUE);
        final Map mapFromStore = nativeStore.getState().getNativeMap();

        assertEquals("Object didn't added to store", mapFromStore, testMap);
    }

    @Test
    public void subscribeReactTest() throws Exception {
        final ReactObserverStub observer = subscribeReact(REACT_OBSERVER_NAME);
        changeStoreStateThreeTimes();

        assertTrue("Observer didn't send 3 events, or currentStateMap == null, or currentStateMap no instanceof WritableMap",
                observer.getEventCounter() == 3
                        && observer.getCurrentStateMap() != null
                        && observer.getCurrentStateMap() instanceof WritableMap);
    }

    @Test
    public void unsubscribeReactTest() throws Exception {
        final ReactObserverStub observer = subscribeReact(REACT_OBSERVER_NAME);
        unsubscribe(observer);
        changeStoreStateThreeTimes();

        assertEquals("The observer was not unsubscribe", observer.getEventCounter(), 0);
    }

    @Test
    public void subscribeNativeTest() throws Exception {
        final NativeObserverStub observer = subscribeNative();
        changeStoreStateThreeTimes();

        assertTrue("Observer didn't receive 3 updates, or currentStateMap == null, or currentStateMap no instanceof Map",
                observer.getUpdateCounter() == 3
                        && observer.getCurrentStateMap() != null
                        && observer.getCurrentStateMap() instanceof Map);
    }

    @Test
    public void unsubscribeNativeTest() throws Exception {
        final NativeObserverStub observer = subscribeNative();
        unsubscribe(observer);
        changeStoreStateThreeTimes();

        assertEquals("The observer was not unsubscribe", observer.getUpdateCounter(), 0);
    }

    private Map<String, Object> setValue(final String key, final String value) {
        final HashMap<String, Object> testMap = new HashMap<>();
        testMap.put(key, value);
        nativeStore.setState(testMap);
        return testMap;
    }


    private ReactObserverStub subscribeReact(final String observerName) {
        final ReactObserverStub observerStub = new ReactObserverStub(observerName);
        nativeStore.registerObserver(observerStub);
        return observerStub;
    }

    private NativeObserverStub subscribeNative() {
        final NativeObserverStub observerStub = new NativeObserverStub();
        nativeStore.registerObserver(observerStub);
        return observerStub;
    }

    private void unsubscribe(final Observer observer) {
        nativeStore.removeObserver(observer);
    }

    private void changeStoreStateThreeTimes() {
        for (int i = 0; i < 3; i++) {
            setValue(TEST_KEY + i, TEST_VALUE + i);
        }
    }
}