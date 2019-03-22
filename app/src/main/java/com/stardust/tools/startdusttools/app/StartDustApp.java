package com.stardust.tools.startdusttools.app;

import android.app.Application;
import android.os.StrictMode;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.baidu.mapapi.SDKInitializer;
import com.stardust.tools.startdusttools.BuildConfig;
import com.stardust.tools.startdusttools.aliyunoss.sercetkey.KeyUtils;

/**
 * Created on 2018/8/24.
 *
 * @author siasun-wangchongyang
 */
public class StartDustApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());

        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
            //Logger.addLogAdapter(new AndroidLogAdapter());
        }

        //阿里云oss 初始化
        //String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        //OSS oss = new OSSClient(getApplicationContext(), endpoint, KeyUtils.getOSSCustomSignerCredentialProvider());

    }
}
