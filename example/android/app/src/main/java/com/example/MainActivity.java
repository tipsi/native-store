package com.example;

import com.example.dummy.GeneratorRandomString;
import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {

    private GeneratorRandomString generatorRandomeString;

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "example";
    }

    //Start-stop GeneratorRandomString for emulate change store state from native
    @Override
    protected void onResume() {
        super.onResume();
        generatorRandomeString = new GeneratorRandomString();
        generatorRandomeString.execute();
    }

    @Override
    protected void onPause() {
        generatorRandomeString.close();
        super.onPause();
    }
}
