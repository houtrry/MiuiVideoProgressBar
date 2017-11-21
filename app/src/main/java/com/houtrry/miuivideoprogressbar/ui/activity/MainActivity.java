package com.houtrry.miuivideoprogressbar.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.houtrry.miuivideoprogressbar.R;
import com.houtrry.miuivideoprogressbar.ui.widget.MIUIVideoProgressBar;

/**
 * 仿小米视频加载进度条
 */
public class MainActivity extends AppCompatActivity {

    private MIUIVideoProgressBar mMiuiVideoProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMiuiVideoProgressBar = (MIUIVideoProgressBar) findViewById(R.id.miuiVideoProgressBar);
    }

    public void start(View view) {
        mMiuiVideoProgressBar.startAnimation();
    }

    public void stop(View view) {
        mMiuiVideoProgressBar.clearAnimator();
    }
}
