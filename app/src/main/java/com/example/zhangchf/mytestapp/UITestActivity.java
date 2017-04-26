package com.example.zhangchf.mytestapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangchf on 25/04/2017.
 */

public class UITestActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.tvShowInput)
    TextView tvShowInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ui_test);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.displayInput)
    protected void onDisplayInput(View v) {
        tvShowInput.setText(editText.getText().toString());
    }
}
