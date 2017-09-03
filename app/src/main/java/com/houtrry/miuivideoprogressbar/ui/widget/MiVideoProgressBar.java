package com.houtrry.miuivideoprogressbar.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by houtrry on 2017/9/2.
 * 小米视频加载进度条
 */

public class MiVideoProgressBar extends ViewGroup {

    public MiVideoProgressBar(Context context) {
        this(context, null);
    }

    public MiVideoProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiVideoProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }
}
