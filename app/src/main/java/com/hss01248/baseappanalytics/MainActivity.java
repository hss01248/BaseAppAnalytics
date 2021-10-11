package com.hss01248.baseappanalytics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

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
}