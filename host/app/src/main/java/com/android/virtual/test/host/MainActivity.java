package com.android.virtual.test.host;

import com.didi.virtualapk.PluginManager;
import com.didi.virtualapk.internal.LoadedPlugin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "VirtualApk";

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 20171222;

    private final String PLUGIN1_PATH_NAME = "/sdcard/plugin1.apk";

    private final String PLUGIN1_PKG_NAME = "com.android.virtual.plugin1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.id_btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission()) {
                    requestPermission();
                    return;
                }

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

    private boolean hasPermission() {

        Log.d(TAG,"hasPermission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }


    private void requestPermission() {

        Log.d(TAG,"requestPermission");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE_STORAGE == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
