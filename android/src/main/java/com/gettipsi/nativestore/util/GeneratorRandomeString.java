package com.gettipsi.nativestore.util;

import android.os.AsyncTask;

/**
 * Created by dmitriy on 11/28/16
 */
public class GeneratorRandomeString extends AsyncTask<Void, String, Void>{


    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    public interface RandomStringListener {
        void onNewString(String s);
    }
}
