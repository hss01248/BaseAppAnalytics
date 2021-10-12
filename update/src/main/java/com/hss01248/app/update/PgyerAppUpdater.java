package com.hss01248.app.update;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.google.gson.Gson;


import org.json.JSONObject;

public class PgyerAppUpdater {


    /**
     *  String url = "https://www.pgyer.com/apiv2/app/check?" +
     *                 "_api_key=38d98d28e5f7dc90d1cb0401d207b86b&token=fc73bc9c4739a97afab38e0ba2667184&buildVersion=13";
     * @param activity
     */
    public static void checkUpdate(Activity activity,String apiKey,String token) {
        String url = "https://www.pgyer.com/apiv2/app/check?" +
                "_api_key="+apiKey+"&token="+token+"&buildVersion="+packageCode(activity.getApplication());
        /**
         * {"code":0,"message":"","data":{"buildBuildVersion":"4","forceUpdateVersion":"","forceUpdateVersionNo":"","needForceUpdate":false,"downloadURL":"https:\/\/www.pgyer.com\/app\/installUpdate\/cdea5c8f7ba3d14dbec7ea360de31fb5?sig=XxP%2B33KaBr7xLDn8p6d336H0fzbL8aPEBL3uA99v%2FH1JBceCNy0eRGuOW99laWVT&forceHttps=","buildHaveNewVersion":true,"buildVersionNo":"15","buildVersion":"1.2.2","buildUpdateDescription":"","buildShortcutUrl":"https:\/\/www.pgyer.com\/YVeW","appKey":"816892cb091ed65eaaa470b2f75df1f2","buildKey":"cdea5c8f7ba3d14dbec7ea360de31fb5","buildName":"FinalCompress","buildIcon":"https:\/\/www.pgyer.com\/image\/view\/app_icons\/657caca8d3f523380fa2bd6026e9af74\/120","buildFileKey":"e0de2f8cdeb25dd09cbff2db34a20f29.apk","buildFileSize":"22072386"}}
         */
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("查询更新中...");
        dialog.show();
        AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl(url)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String result) {
                        try {
                            dialog.dismiss();
                            JSONObject json = new JSONObject(result);
                            if(json.optInt("code") != 0){
                                toast(activity.getApplication(),json.optString("message"));
                                return null;
                            }
                            PgyerUpdateInfo info = new Gson().fromJson(json.optString("data"),PgyerUpdateInfo.class);
                            if(info == null){
                                onRequestVersionFailure("data parse failed");
                                return null;
                            }
                            int lastVersion = Integer.parseInt(info.buildVersionNo);
                            if(packageCode(activity.getApplication()) >= lastVersion){
                                onRequestVersionFailure("已经是最新版本");
                                return null;
                            }
                            StringBuilder des = new StringBuilder();
                            des.append("新版本大小:")
                                    .append(Long.parseLong(info.buildFileSize)/1024/1024)
                                    .append("M")
                                    .append("\n\n")
                                    .append(info.buildUpdateDescription);

                            //downloadBuilder.setDownloadAPKPath(activity.getApplication().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

                            UIData uiData = UIData.create().setContent(des.toString())
                                    .setTitle("是否升级到"+info.buildVersion+"版本?")
                                    .setDownloadUrl(info.downloadURL);
                            return uiData;


                        } catch (Exception e) {
                            e.printStackTrace();
                            onRequestVersionFailure(e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        dialog.dismiss();
                        toast(activity.getApplication(),message);

                    }
                }).executeMission(activity.getApplication());
    }

    private static void toast(Application application, String message) {
        Toast.makeText(application,message,Toast.LENGTH_LONG).show();
    }

    private static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

}
