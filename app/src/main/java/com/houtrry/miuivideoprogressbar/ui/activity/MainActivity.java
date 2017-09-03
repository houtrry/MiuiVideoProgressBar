package com.houtrry.miuivideoprogressbar.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.houtrry.miuivideoprogressbar.R;
import com.houtrry.miuivideoprogressbar.ui.widget.TriangleView;

/**
 * 仿小米视频加载进度条
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TriangleView triangleView = (TriangleView) findViewById(R.id.triangleView);
        triangleView.setProgress(0.2f, true);
    }
}
