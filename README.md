# BaseAppAnalytics

app分析,统计,崩溃日志,更新...

umeng+bugly

# 使用: 

远程脚本+插件配置

根目录的build.gradle下:

```groovy
buildscript {
apply from : 'https://raw.githubusercontent.com/hss01248/BaseAppAnalytics/master/remote.gradle'
  ...
```

app的build.gradle里:

```groovy
apply plugin: 'bugly'

bugly {
    debug = true
    appId = 'xxxx' // 注册时分配的App ID
    appKey = 'xxxxxx' // 注册时分配的App Key
}

//友盟
apply plugin: 'com.efs.sdk.plugin'
efs {
    //是否对启动过程进程插桩的开关，如果使用自动集成监控则必须开启，false则不开启启动插件
    isAutoTrack = true
    //您自定义Application的类名称，必填项，如没有自定义则填写系统Application
    applicationName = "com.xxxxx.baseappanalytics.BaseApp"
    //您自定义Activity的类名称，必填项，将您所有用于页面展示的Activity的类名按如下格式填写，否则会丢失热启动
}
```

