package com.houtrry.miuivideoprogressbar.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
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
        startAnimation();
    }

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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //        canvas.drawColor(Color.parseColor("#A9A9A9"));
        handleValue(canvas);
        mPaint.setColor(Color.GREEN);
        //        canvas.drawPoint(mPointFs[0].x, mPointFs[0].y, mPaint);
        //        canvas.drawPoint(mPointFs[1].x, mPointFs[1].y, mPaint);
        //        canvas.drawPoint(mPointFs[2].x, mPointFs[2].y, mPaint);
        //        canvas.drawPoint(mPointFs[3].x, mPointFs[3].y, mPaint);
        //        canvas.drawPoint(mPointFs[4].x, mPointFs[4].y, mPaint);
        //        canvas.drawPoint(mPointFs[5].x, mPointFs[5].y, mPaint);
    }

    private float progress = 1.0f;
    private boolean isReverse = false;

    public void setProgress(float progress) {
        this.progress = progress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void startAnimation() {
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
        mObjectAnimator.cancel();
        this.clearAnimation();
    }


    private void handleValue(Canvas canvas) {
        drawFirst(canvas);
        drawSecond(canvas);
        drawThird(canvas);
        drawFourth(canvas);
    }

    private Path firstPath = new Path();
    private Path secondPath = new Path();
    private Path thirdPath = new Path();
    private Path fourthPath = new Path();
    private float progressForFirst = 0;
    private float progressForSecond = 0;
    private float progressForThird = 0;
    private float progressForFourth = 0;

    private void drawFirst(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#B990CD"));
        firstPath.reset();
        progressForFirst = progress;
        if (progressForFirst > 1) {
            progressForFirst = 1;
        } else if (progressForFirst < 0) {
            progressForFirst = 0;
        }
        if (isReverse) {
            firstPath.moveTo(mPointFs[3].x, mPointFs[3].y);
            firstPath.lineTo(mPointFs[3].x + (mPointFs[1].x - mPointFs[3].x) * progressForFirst, mPointFs[3].y + (mPointFs[1].y - mPointFs[3].y) * progressForFirst);
            firstPath.lineTo(mPointFs[3].x + (mPointFs[4].x - mPointFs[3].x) * progressForFirst, mPointFs[3].y + (mPointFs[4].y - mPointFs[3].y) * progressForFirst);
            firstPath.close();
        } else {
            firstPath.moveTo(mPointFs[4].x, mPointFs[4].y);
            firstPath.lineTo(mPointFs[4].x + (mPointFs[1].x - mPointFs[4].x) * progressForFirst, mPointFs[4].y + (mPointFs[1].y - mPointFs[4].y) * progressForFirst);
            firstPath.lineTo(mPointFs[4].x + (mPointFs[3].x - mPointFs[4].x) * progressForFirst, mPointFs[4].y + (mPointFs[3].y - mPointFs[4].y) * progressForFirst);
            firstPath.close();
        }
        canvas.drawPath(firstPath, mPaint);
    }

    private void drawSecond(Canvas canvas) {
        progressForSecond = progress - 1;
        if (progressForSecond > 1) {
            progressForSecond = 1;
        } else if (progressForSecond < 0) {
            progressForSecond = 0;
        }
        secondPath.reset();
        if (isReverse) {//
            mPaint.setColor(Color.parseColor("#58B8C2"));
            secondPath.moveTo(mPointFs[4].x, mPointFs[4].y);
            secondPath.lineTo(mPointFs[4].x + (mPointFs[3].x - mPointFs[4].x) * progressForSecond, mPointFs[4].y + (mPointFs[3].y - mPointFs[4].y) * progressForSecond);
            secondPath.lineTo(mPointFs[4].x + (mPointFs[5].x - mPointFs[4].x) * progressForSecond, mPointFs[4].y + (mPointFs[5].y - mPointFs[4].y) * progressForSecond);
            secondPath.close();
        } else {
            mPaint.setColor(Color.parseColor("#FAAA21"));
            secondPath.moveTo(mPointFs[1].x, mPointFs[1].y);
            secondPath.lineTo(mPointFs[1].x + (mPointFs[3].x - mPointFs[1].x) * progressForSecond, mPointFs[1].y + (mPointFs[3].y - mPointFs[1].y) * progressForSecond);
            secondPath.lineTo(mPointFs[1].x + (mPointFs[0].x - mPointFs[1].x) * progressForSecond, mPointFs[1].y + (mPointFs[0].y - mPointFs[1].y) * progressForSecond);
            secondPath.close();
        }
        canvas.drawPath(secondPath, mPaint);
    }

    private void drawThird(Canvas canvas) {
        progressForThird = progress - 2;
        if (progressForThird > 1) {
            progressForThird = 1;
        } else if (progressForThird < 0) {
            progressForThird = 0;
        }
        thirdPath.reset();
        if (isReverse) {//
            mPaint.setColor(Color.parseColor("#FAAA21"));
            thirdPath.moveTo(mPointFs[3].x, mPointFs[3].y);
            thirdPath.lineTo(mPointFs[3].x + (mPointFs[0].x - mPointFs[3].x) * progressForThird, mPointFs[3].y + (mPointFs[0].y - mPointFs[3].y) * progressForThird);
            thirdPath.lineTo(mPointFs[3].x + (mPointFs[1].x - mPointFs[3].x) * progressForThird, mPointFs[3].y + (mPointFs[1].y - mPointFs[3].y) * progressForThird);
            thirdPath.close();
        } else {
            mPaint.setColor(Color.parseColor("#58B8C2"));
            thirdPath.moveTo(mPointFs[3].x, mPointFs[3].y);
            thirdPath.lineTo(mPointFs[3].x + (mPointFs[4].x - mPointFs[3].x) * progressForThird, mPointFs[3].y + (mPointFs[4].y - mPointFs[3].y) * progressForThird);
            thirdPath.lineTo(mPointFs[3].x + (mPointFs[5].x - mPointFs[3].x) * progressForThird, mPointFs[3].y + (mPointFs[5].y - mPointFs[3].y) * progressForThird);
            thirdPath.close();
        }
        canvas.drawPath(thirdPath, mPaint);
    }

    private void drawFourth(Canvas canvas) {
        progressForFourth = progress - 3;
        if (progressForFourth > 1) {
            progressForFourth = 1;
        } else if (progressForFourth < 0) {
            progressForFourth = 0;
        }
        mPaint.setColor(Color.parseColor("#EA7A79"));
        fourthPath.reset();
        if (isReverse) {//
            fourthPath.moveTo(mPointFs[1].x, mPointFs[1].y);
            fourthPath.lineTo(mPointFs[1].x + (mPointFs[2].x - mPointFs[1].x) * progressForFourth, mPointFs[1].y + (mPointFs[2].y - mPointFs[1].y) * progressForFourth);
            fourthPath.lineTo(mPointFs[1].x + (mPointFs[4].x - mPointFs[1].x) * progressForFourth, mPointFs[1].y + (mPointFs[4].y - mPointFs[1].y) * progressForFourth);
            fourthPath.close();
        } else {
            fourthPath.moveTo(mPointFs[4].x, mPointFs[4].y);
            fourthPath.lineTo(mPointFs[4].x + (mPointFs[1].x - mPointFs[4].x) * progressForFourth, mPointFs[4].y + (mPointFs[1].y - mPointFs[4].y) * progressForFourth);
            fourthPath.lineTo(mPointFs[4].x + (mPointFs[2].x - mPointFs[4].x) * progressForFourth, mPointFs[4].y + (mPointFs[2].y - mPointFs[4].y) * progressForFourth);
            fourthPath.close();
        }
        canvas.drawPath(fourthPath, mPaint);
    }
}
