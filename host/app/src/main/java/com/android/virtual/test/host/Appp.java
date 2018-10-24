package com.android.virtual.test.host;

import com.didi.virtualapk.PluginManager;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * des:
 * author: libingyan
 * Date: 18-10-24 11:10
 */
public class Appp extends Application {

    private final String TAG = "App";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        long start = System.currentTimeMillis();
        PluginManager.getInstance(base).init();
        Log.d(TAG, "use time: " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
