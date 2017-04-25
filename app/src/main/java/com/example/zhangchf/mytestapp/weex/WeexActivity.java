package com.example.zhangchf.mytestapp.weex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.zhangchf.mytestapp.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangchf on 05/04/2017.
 */

public class WeexActivity extends AppCompatActivity implements IWXRenderListener {
    private static final String TAG = WeexActivity.class.getSimpleName();

    WXSDKInstance wxsdkInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weex);

        wxsdkInstance = new WXSDKInstance(this);
        wxsdkInstance.registerRenderListener(this);

        Map<String,Object> options=new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL,"file://build/hello.js");
        wxsdkInstance.render("WXSample", WXFileUtils.loadAsset("build/hello.js", this), null, null, WXRenderStrategy.APPEND_ASYNC);
        Log.i(TAG, "weex render");
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.i(TAG, "onViewCreated");
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.i(TAG, "onRenderSuccess");
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.i(TAG, "onRefreshSuccess");
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.i(TAG, "onException:" + errCode + "," + msg);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityDestroy();
        }
    }
}
