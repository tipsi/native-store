package com.gettipsi.nativestore;

import com.gettipsi.nativestore.store.NativeStore;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Before
    public void init() throws Exception {
        NativeStore.getInstance();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testInstanceCreation() throws Exception {
        assertEquals(NativeStore.getInstance() != null, true);
    }

    @Test
    public void changeDataTest() throws Exception {
        final HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("test_key", "test_value");
        NativeStore.getInstance().changeData("test_map_name", testMap);
        assertEquals(NativeStore.getInstance() != null, true);
    }
}