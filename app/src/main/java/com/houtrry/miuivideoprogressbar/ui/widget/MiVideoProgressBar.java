package com.houtrry.miuivideoprogressbar.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by houtrry on 2017/9/2.
 * 小米视频加载进度条
 */

public class MiVideoProgressBar extends ViewGroup {

    private final static String TAG = MiVideoProgressBar.class.getSimpleName();
    private int mWidth;
    private int mHeight;
    private PointF mCenterPoint;
    private double mSin60;
    private double mCos60;

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
        mSin60 = Math.sin(60 * Math.PI / 180d);
        mCos60 = Math.cos(60 * Math.PI / 180d);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCenterPoint = new PointF(mWidth*0.5f, mHeight*0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout: changed: "+changed+", left/top/right/bottom: "+left+"/"+top+"/"+right+"/"+bottom);
        View childView0 = getChildAt(0);
        childView0.layout((int)(mCenterPoint.x - childView0.getMeasuredWidth()*mSin60),
                (int)(mCenterPoint.y - childView0.getMeasuredWidth()*0.5f),
                (int)(mCenterPoint.x),
                (int)(mCenterPoint.y + childView0.getMeasuredWidth()*0.5f));
        childView0.setRotation(00);


//        View childView1 = getChildAt(1);
//        childView1.layout((int)(mCenterPoint.x - childView1.getMeasuredWidth()*mSin60),
//                (int)(mCenterPoint.y - childView1.getMeasuredWidth()*0.5f),
//                (int)(mCenterPoint.x),
//                (int)(mCenterPoint.y + childView1.getMeasuredWidth()*0.5f));
    }
}
