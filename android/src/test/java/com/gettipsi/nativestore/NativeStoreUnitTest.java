package com.gettipsi.nativestore;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.WritableMap;
import com.gettipsi.nativestore.store.NativeStore;
import com.gettipsi.nativestore.store.Observer;
import com.gettipsi.nativestore.util.ReactObserverStub;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Arguments.class})
public class NativeStoreUnitTest {

    private static final String REACT_OBSERVER_NAME = "REACT_OBSERVER_NAME";
    private static final String TEST_MAP_NAME = "test_map_name";

    private Map<String, Observer> observerMap;

    //Without this lines I have the same problem:
    // http://stackoverflow.com/questions/35275772/unsatisfiedlinkerror-when-unit-testing-writablenativemap
    @PrepareForTest({Arguments.class})
    @Before
    public void setUp(){
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
    }

    @Before
    public void init() throws Exception {
        NativeStore.getInstance();
        observerMap = new HashMap<>();
    }


    @Test
    public void testInstanceCreation() throws Exception {
        assertEquals(NativeStore.getInstance() != null, true);
    }

    @Test
    public void subscribeTest() throws Exception {
        final ReactObserverStub observer = new ReactObserverStub(REACT_OBSERVER_NAME);
        observerMap.put(REACT_OBSERVER_NAME, observer);
        NativeStore.getInstance().registerObserver(observer);
        assertEquals(NativeStore.getInstance() != null, true);
    }


    @Test
    public void setValueTest() throws Exception {
        final HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("test_key", "test_value");
        NativeStore.getInstance().changeData("test_map_name", testMap);
        assertEquals(NativeStore.getInstance().getItem(TEST_MAP_NAME) , testMap);
    }

    @Test
    public void unsubscribeTest() throws Exception {
        final ReactObserverStub observer = (ReactObserverStub) observerMap.get(REACT_OBSERVER_NAME);
        if (observer != null) {
            NativeStore.getInstance().removeObserver(observer);
            observerMap.remove(REACT_OBSERVER_NAME);
        }
        assertEquals(NativeStore.getInstance() != null, true);
    }

    @Test
    public void getValueTest() throws Exception {
        final WritableMap item = NativeStore.getInstance().getItem(TEST_MAP_NAME);
        assertEquals(item != null , true);
    }

    @Test
    public void removeValueTest() throws Exception {
        NativeStore.getInstance().removeData(TEST_MAP_NAME);
        assertNull("Object did't remove from store", NativeStore.getInstance().getItem(TEST_MAP_NAME));
    }
}