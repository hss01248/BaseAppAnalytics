package com.hss01248.baseappanalytics;

import android.app.Application;

import com.hss01248.analytics_umeng.UmengUtil;
import com.hss01248.bugly.BuildConfig;
import com.hss01248.bugly.XReporter;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XReporter.init(this,"7ac352d904",true);
        //4f7a08bf-1fa1-453f-870d-da59f0131c02
        UmengUtil.init(this,"6163f5bbac9567566e91bb94","bugly",1,"", BuildConfig.DEBUG);
    }
}
