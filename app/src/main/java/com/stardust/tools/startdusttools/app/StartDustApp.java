package com.stardust.tools.startdusttools.app;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

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
    }
}
