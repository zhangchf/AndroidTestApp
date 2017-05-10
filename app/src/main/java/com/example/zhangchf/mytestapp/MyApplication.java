package com.example.zhangchf.mytestapp;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.example.zhangchf.mytestapp.weex.ImageAdapter;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

/**
 * Created by zhangchf on 05/04/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        // Weex Configuration
        InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
        WXSDKEngine.initialize(this, config);
    }


}
