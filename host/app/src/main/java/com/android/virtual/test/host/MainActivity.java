package com.android.virtual.test.host;

import com.didi.virtualapk.PluginManager;
import com.didi.virtualapk.internal.LoadedPlugin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "VirtualApk";

    private final String PLUGIN1_PATH_NAME = "/sdcard/plugin1.apk";

    private final String PLUGIN1_PKG_NAME = "com.android.virtual.plugin1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.id_btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File apk = new File(PLUGIN1_PATH_NAME);
                if (apk.exists() && apk.length() > 0) {
                    try {
                        PluginManager.getInstance(MainActivity.this).loadPlugin(apk);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, PLUGIN1_PATH_NAME + " file not exists!!!");
                }
            }
        });

        findViewById(R.id.id_start_plugin1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadedPlugin plugin1 = PluginManager.getInstance(MainActivity.this).getLoadedPlugin(PLUGIN1_PKG_NAME);
                Log.i(TAG , "plugin1: " + plugin1);

                if (plugin1 != null) {
                    final Intent pluginIntent = new Intent();
                    pluginIntent.setClassName("com.android.virtual.test.host", "com.android.virtual.plugin1.Plugin1Activity");
                    startActivity(pluginIntent);
                }
            }
        });
    }
}
