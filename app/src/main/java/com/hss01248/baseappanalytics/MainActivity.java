package com.hss01248.baseappanalytics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hss01248.app.update.PgyerAppUpdater;
import com.hss01248.bugly.XReporter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void crash(View view) {
        int i = 1/0;
    }

    public void exception(View view) {
        try {
            Object a = null;
            a.getClass();
        }catch (Throwable throwable){
            XReporter.reportException(throwable);
        }

    }

    public void checkUpdate(View view) {
        PgyerAppUpdater.checkUpdate(this,"38d98d28e5f7dc90d1cb0401d207b86b","fc73bc9c4739a97afab38e0ba2667184");
    }
}