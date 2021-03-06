package com.example.dummy;

import android.os.AsyncTask;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.gettipsi.nativestore.store.NativeStore;

import java.util.Random;

/**
 * Created by dmitriy on 11/28/16
 */
public class GeneratorRandomString extends AsyncTask<Void, String, Void> {

    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    private static final int S_LENGTH = 21;
    private boolean needString;

    public GeneratorRandomString() {
        needString = true;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        while (needString) {
            publishProgress(getRandomString(S_LENGTH));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        changeStateFromNative(values[0]);
    }

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    //Emulate changes store state from native
    private void changeStateFromNative(String s) {
        final WritableMap map;
        if (NativeStore.getInstance().getState() != null) {
            map = NativeStore.getInstance().getState().getWritableMap();
        } else {
            map = Arguments.createMap();
        }
        map.putString("uuid", s);
        NativeStore.getInstance().setState(map);
    }

    public void close() {
        needString = false;
        cancel(true);
    }
}
