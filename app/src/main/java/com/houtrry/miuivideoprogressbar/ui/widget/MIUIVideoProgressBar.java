package com.houtrry.miuivideoprogressbar.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
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
 * @desc 小米视频加载时的动画
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
    private long mDuration = 2000;
    private TimeInterpolator mTimeInterpolator = new AccelerateDecelerateInterpolator();

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


    private TriangleView mTriangleView0;
    private TriangleView mTriangleView1;
    private TriangleView mTriangleView2;
    private TriangleView mTriangleView3;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //计算三角形的边长(这里取高度的一半)
        mTriangleWidth = mHeight * 0.5f;
        //计算三角形的高度
        mTriangleHeight = (float) (mTriangleWidth * Math.sin(60d * Math.PI / 180));
        //取整个View中心点的坐标
        mCenterPoint = new PointF(mWidth * 0.5f, mHeight * 0.5f);

        //计算三角形的六个点的坐标
        mPointFs = new PointF[6];
        mPointFs[0] = new PointF(mCenterPoint.x - mTriangleHeight, mCenterPoint.y - mTriangleWidth * 1.0f);
        mPointFs[1] = new PointF(mCenterPoint.x - mTriangleHeight, mCenterPoint.y);
        mPointFs[2] = new PointF(mCenterPoint.x - mTriangleHeight, mCenterPoint.y + mTriangleWidth * 1.0f);
        mPointFs[3] = new PointF(mCenterPoint.x, mCenterPoint.y - mTriangleWidth * 0.5f);
        mPointFs[4] = new PointF(mCenterPoint.x, mCenterPoint.y + mTriangleWidth * 0.5f);
        mPointFs[5] = new PointF(mCenterPoint.x + mTriangleHeight, mCenterPoint.y);

        //初始化四个小三角形
        mTriangleView0 = new TriangleView(mPointFs[4], mPointFs[3], mPointFs[1], Color.parseColor("#B990CD"));
        mTriangleView1 = new TriangleView(mPointFs[1], mPointFs[3], mPointFs[0], Color.parseColor("#FAAA21"));
        mTriangleView2 = new TriangleView(mPointFs[3], mPointFs[4], mPointFs[5], Color.parseColor("#58B8C2"));
        mTriangleView3 = new TriangleView(mPointFs[4], mPointFs[1], mPointFs[2], Color.parseColor("#EA7A79"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTriangleView0.drawTriangleView(canvas, mPaint, progress, isReverse);
        mTriangleView1.drawTriangleView(canvas, mPaint, progress - (isReverse ? 2 : 1), isReverse);
        mTriangleView2.drawTriangleView(canvas, mPaint, progress - (isReverse ? 1 : 2), isReverse);
        mTriangleView3.drawTriangleView(canvas, mPaint, progress - 3, isReverse);
    }

    /**
     * 当前进度.
     * (0.0~4.0)的值
     */
    private float progress = 0.0f;
    /**
     * 是否是反向.
     * true:  反向, 表示当前进度由大减小
     * false: 正向, 表示当前进度由小增大
     */
    private boolean isReverse = false;

    public void setProgress(float progress) {
        this.progress = progress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void setTimeInterpolator(TimeInterpolator timeInterpolator) {
        mTimeInterpolator = timeInterpolator;
    }

    public void startAnimation() {
        if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
            mObjectAnimator.cancel();
        }
        isReverse = false;
        mObjectAnimator = ObjectAnimator.ofFloat(this, "progress", 0f, 4.0f);
        mObjectAnimator.setDuration(mDuration);
        mObjectAnimator.setInterpolator(mTimeInterpolator);
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
        mObjectAnimator.start();
    }

    public void clearAnimation() {
        if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
            mObjectAnimator.cancel();
            this.clearAnimation();
            mObjectAnimator.addListener(null);
            mObjectAnimator.addUpdateListener(null);
        }
    }

    private static class TriangleView {
        /**
         * 三角形的起始点.
         * 也就是三角形慢慢变大时, 三角形的顶点.
         */
        private PointF startPoint;
        /**
         * 三角形的结束点.
         * 三角形变小时, 三角形的顶点.
         */
        private PointF endPoint;
        /**
         * 三角形除了起始点和结束点外的第三个点.
         */
        private PointF thirdPoint;
        private Path mPath = new Path();

        private int color;

        public TriangleView() {
        }

        public TriangleView(PointF startPoint, PointF endPoint, PointF thirdPoint, int color) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.thirdPoint = thirdPoint;
            this.color = color;
        }

        public PointF getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(PointF startPoint) {
            this.startPoint = startPoint;
        }

        public PointF getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(PointF endPoint) {
            this.endPoint = endPoint;
        }

        public PointF getThirdPoint() {
            return thirdPoint;
        }

        public void setThirdPoint(PointF thirdPoint) {
            this.thirdPoint = thirdPoint;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void drawTriangleView(Canvas canvas, Paint paint, float progress, boolean isReverse) {
            progress = checkProgress(progress);
            mPath.reset();
            paint.setColor(color);
            if (isReverse) {
                mPath.moveTo(endPoint.x, endPoint.y);
                mPath.lineTo(endPoint.x + (startPoint.x - endPoint.x) * progress, endPoint.y + (startPoint.y - endPoint.y) * progress);
                mPath.lineTo(endPoint.x + (thirdPoint.x - endPoint.x) * progress, endPoint.y + (thirdPoint.y - endPoint.y) * progress);
                mPath.close();
            } else {
                mPath.moveTo(startPoint.x, startPoint.y);
                mPath.lineTo(startPoint.x + (endPoint.x - startPoint.x) * progress, startPoint.y + (endPoint.y - startPoint.y) * progress);
                mPath.lineTo(startPoint.x + (thirdPoint.x - startPoint.x) * progress, startPoint.y + (thirdPoint.y - startPoint.y) * progress);
                mPath.close();
            }
            canvas.drawPath(mPath, paint);
        }

        private float checkProgress(float progress) {
            if (progress < 0) {
                progress = 0;
            } else if (progress > 1) {
                progress = 1;
            }
            return progress;
        }

        @Override
        public String toString() {
            return "TriangleView{" +
                    "startPoint=" + startPoint +
                    ", endPoint=" + endPoint +
                    ", thirdPoint=" + thirdPoint +
                    ", mPath=" + mPath +
                    ", color=" + color +
                    '}';
        }
    }
}
