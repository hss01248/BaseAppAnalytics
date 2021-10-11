package com.hss01248.analytics_umeng;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.uc.crashsdk.export.CrashApi;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.umcrash.UMCrash;
import com.umeng.umcrash.UMCrashCallback;

import java.util.Map;

public class UmengUtil {

    //6134886e695f794bbd9fb14e
    public static void init(Application application, String appkey, String channel, int deviceType, String pushSecret,boolean enableLog){
        UMConfigure.setLogEnabled(enableLog);
        //1.隐私合规中加入友盟+SDK合规声明https://developer.umeng.com/docs/147377/detail/213789

//2.在Applicaiton.onCreate函数中调用预初始化函数UMConfigure.preInit()
//preInit预初始化函数耗时极少，不会影响App首次冷启动用户体验，不会采集设备信息，也不会向友盟后台上报数据。
        //public static void preInit(Context context, String appkey, String channel)

//3.客户端用户同意隐私政策后，正式初始化友盟+SDK
        UMConfigure.init(application, appkey,  channel,  deviceType,  pushSecret);

        //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
//建议在宿主App的Application.onCreate函数中调用此函数。
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);



        //apm
        //如果您使用我们的SDK捕获native崩溃后，其他捕获工具无法捕获到native 崩溃，可以使用如下方法在初始化SDK后进行设置，是其他SDK可以捕获到native 崩溃
        final Bundle customInfo = new Bundle();
        customInfo.putBoolean("mCallNativeDefaultHandler",true);
        CrashApi.getInstance().updateCustomInfo(customInfo);

        UMCrash.registerUMCrashCallback(new UMCrashCallback(){
            @Override
            public String onCallback(){
                return"崩溃时register的自定义内容字符串";
            }
        });

        //public static void UMCrash.generateCustomLog(Throwable e,String type)
        //public static void UMCrash.generateCustomLog(String e,String type)


    }

     public static void onEventObject(Context context, String eventID, Map<String, Object> map){
         MobclickAgent.onEventObject(context, eventID, map);
     }
}
