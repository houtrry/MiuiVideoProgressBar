package com.houtrry.miuivideoprogressbar.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author houtrry
 * @version $Rev$
 * @time 2017/9/8 9:33
 * @desc ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDesc $TODO$
 */

public class MIUIVideoProgressBar extends View {

    private static final String TAG = MIUIVideoProgressBar.class.getSimpleName();
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private float mTriangleWidth;
    private float mTriangleHeight;
    private PointF mCenterPoint;
    private PointF[] mPointFs;
    private ObjectAnimator mObjectAnimator;

    public MIUIVideoProgressBar(Context context) {
        this(context, null);
    }

    public MIUIVideoProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MIUIVideoProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //        startAnimation();
    }


    private TriangleView1 mTriangleView1_0;
    private TriangleView1 mTriangleView1_1;
    private TriangleView1 mTriangleView1_2;
    private TriangleView1 mTriangleView1_3;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mTriangleWidth = mHeight * 0.5f;
        mTriangleHeight = (float) (mTriangleWidth * Math.sin(60d * Math.PI / 180));

        mCenterPoint = new PointF(mWidth * 0.5f, mHeight * 0.5f);


        mPointFs = new PointF[6];
        mPointFs[0] = new PointF(mCenterPoint.x - mTriangleHeight, mCenterPoint.y - mTriangleWidth * 1.0f);
        mPointFs[1] = new PointF(mCenterPoint.x - mTriangleHeight, mCenterPoint.y);
        mPointFs[2] = new PointF(mCenterPoint.x - mTriangleHeight, mCenterPoint.y + mTriangleWidth * 1.0f);
        mPointFs[3] = new PointF(mCenterPoint.x, mCenterPoint.y - mTriangleWidth * 0.5f);
        mPointFs[4] = new PointF(mCenterPoint.x, mCenterPoint.y + mTriangleWidth * 0.5f);
        mPointFs[5] = new PointF(mCenterPoint.x + mTriangleHeight, mCenterPoint.y);

        mTriangleView1_0 = new TriangleView1(mPointFs[4], mPointFs[3], mPointFs[1], Color.parseColor("#B990CD"));
        mTriangleView1_1 = new TriangleView1(mPointFs[1], mPointFs[3], mPointFs[0], Color.parseColor("#FAAA21"));
        mTriangleView1_2 = new TriangleView1(mPointFs[3], mPointFs[4], mPointFs[5], Color.parseColor("#58B8C2"));
        mTriangleView1_3 = new TriangleView1(mPointFs[4], mPointFs[1], mPointFs[2], Color.parseColor("#EA7A79"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTriangleView1_0.show(canvas, mPaint, progress, isReverse);
        mTriangleView1_1.show(canvas, mPaint, progress - (isReverse ? 2 : 1), isReverse);
        mTriangleView1_2.show(canvas, mPaint, progress - (isReverse ? 1 : 2), isReverse);
        mTriangleView1_3.show(canvas, mPaint, progress - 3, isReverse);
    }

    private float progress = 1.0f;
    private boolean isReverse = false;

    public void setProgress(float progress) {
        this.progress = progress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void startAnimation() {
        if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
            mObjectAnimator.cancel();
        }
        isReverse = false;
        mObjectAnimator = ObjectAnimator.ofFloat(this, "progress", 0f, 4.0f);
        mObjectAnimator.setDuration(2000);
        mObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mObjectAnimator.setRepeatCount(-1);
        mObjectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isReverse = !isReverse;
            }
        });
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setProgress((Float) animation.getAnimatedValue());
            }
        });
        mObjectAnimator.start();
    }

    public void clearAnimation() {
        Log.d(TAG, "clearAnimation: ");
        if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
            Log.d(TAG, "clearAnimation: cancel");
            mObjectAnimator.cancel();
            this.clearAnimation();
            mObjectAnimator.addListener(null);
            mObjectAnimator.addUpdateListener(null);
        }
    }
}
