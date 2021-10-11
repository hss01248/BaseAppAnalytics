package com.hss01248.baseappanalytics;

import android.app.Application;

import com.hss01248.analytics_umeng.UmengUtil;
import com.hss01248.bugly.BuildConfig;
import com.hss01248.bugly.XReporter;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XReporter.init(this,"b3c263eaa5",true);
        UmengUtil.init(this,"6134886e695f794bbd9fb14e","bugly",1,"", BuildConfig.DEBUG);
    }
}
