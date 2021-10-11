package com.hss01248.bugly;

import android.app.Application;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

public class XReporter {
    static Application application;
    public static void init(Application application,String buglyAppid,boolean debug){
        /*CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(application);
        strategy.setBuglyLogUpload()*/

        //CrashReport.initCrashReport(application, buglyAppid, debug);
        Bugly.init(application, buglyAppid, debug);
        //Beta.checkAppUpgrade();
    }

    public static void reportException(Throwable throwable){
        CrashReport.postCatchedException(throwable);

    }

    public static void reportMsg(String msg){
        BuglyLog.d("msg",msg);
    }

    public static void addGlobalKV(String key,String value){
        CrashReport.setSdkExtraData(application,key,value);
    }
}
