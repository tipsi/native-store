package com.gettipsi.nativestore.util;

import android.os.AsyncTask;

import java.util.Random;

/**
 * Created by dmitriy on 11/28/16
 */
public class GeneratorRandomeString extends AsyncTask<Void, String, Void> {

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
    private static final int S_LENGTH = 21;
    private final RandomStringListener listener;
    private boolean needString;

    public GeneratorRandomeString(RandomStringListener listener) {
        this.listener = listener;
        needString = true;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        while (needString){
            publishProgress(getRandomString(S_LENGTH));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        listener.onNewString(values[0]);
    }

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public void close() {
        needString = false;
        cancel(false);
    }

    public interface RandomStringListener {
        void onNewString(String s);
    }
}
