package com.stardust.tools.startdusttools.app;

import android.app.Application;
import android.os.StrictMode;

import com.baidu.mapapi.SDKInitializer;
import com.stardust.tools.startdusttools.BuildConfig;

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
    }
}
