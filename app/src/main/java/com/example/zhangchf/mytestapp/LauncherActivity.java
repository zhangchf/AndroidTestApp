package com.example.zhangchf.mytestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by zhangchf on 07/02/2017.
 */

public class LauncherActivity extends AppCompatActivity {

    TextView tvLauncherText;

    boolean firstTime = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        tvLauncherText = (TextView) findViewById(R.id.tv_launcher_text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (firstTime) {
            firstTime = false;

            getWindow().getDecorView().getRootView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeScaleUpAnimation(tvLauncherText, 0, 0, 100, 100);
                    activityOptions = ActivityOptionsCompat.makeCustomAnimation(LauncherActivity.this, R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class), activityOptions.toBundle());
//                    LauncherActivity.this.finish();
                }
            }, 2000);
        }

    }
}
