package com.houtrry.miuivideoprogressbar.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.houtrry.miuivideoprogressbar.R;

/**
 * Created by houtrry on 2017/9/2.
 * 进度条拆解, 单独的三角形View.
 * <p>
 * /\
 * /  \
 * /    \
 * /______\
 * <p>
 * 默认左下角的点作为起始点, 右下角的点作为结束点
 */

public class TriangleView extends View {

    private int mBackgroundColorColor = -1;
    private float mSideLength = 50;
    private int mRotateAngle = 0;

    private boolean mIsAdd = true;
    private int mWidth;
    private int mHeight;

    private PointF mCenterPoint = new PointF(0, 0);//控件的中心点

    private PointF mStartPoint = new PointF(0, 0);//起始点, 默认左下角的点的坐标
    private PointF mEndPoint = new PointF(0, 0);//结束点, 默认右下角
    private PointF mThirdPoint = new PointF(0, 0);//三角形中, 除了起始点和结束点外的第三个点


    private Path mTrianglePath = new Path();
    private Paint mPaint;

    private float mProgress = 1.0f;

    public TriangleView(Context context) {
        this(context, null);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        final TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TriangleView);
        mBackgroundColorColor = typeArray.getColor(R.styleable.TriangleView_backgroundColor, context.getResources().getColor(R.color.colorAccent));
        mSideLength = typeArray.getDimension(R.styleable.TriangleView_sideLength, 50);
        mRotateAngle = typeArray.getInt(R.styleable.TriangleView_rotateAngle, 0);
        typeArray.recycle();
    }

    private void initPaint(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBackgroundColorColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        refreshSideLength(getMeasuredWidth(), getMeasuredHeight());


        calculateTrianglePoint();
    }

    private void refreshSideLength(int measuredWidth, int measuredHeight) {
        mWidth = measuredWidth;
        mHeight = measuredHeight;
        final double minHeight = mSideLength * Math.sin(60d * Math.PI / 180d);
        if (mWidth < mSideLength && mHeight < minHeight) {//宽高都不够
            if (mSideLength * mHeight > minHeight * mWidth) {//用宽度计算边长
                calculateSideLength(true);
            } else {//用高度计算边长
                calculateSideLength(false);
            }
        } else if (mWidth < mSideLength) {//宽度不够, 高度够, 用宽度计算边长
            calculateSideLength(true);
        } else if (mHeight < minHeight) {//宽度够, 高度不够, 用高度计算边长
            calculateSideLength(false);
        } else {//宽度高度都够

        }
    }

    private void calculateSideLength(boolean useWidthToCalculate) {
        if (useWidthToCalculate) {
            mSideLength = mWidth;
        } else {
            mSideLength = (float) (mHeight / Math.sin(60d * Math.PI / 180d));
        }
    }

    private void calculateTrianglePoint() {
        mCenterPoint.set(mWidth * 0.5f, mHeight * 0.5f);
        float radius = (float) (mSideLength * 0.5f / Math.cos(30d * Math.PI / 180d));
        mThirdPoint.set(mCenterPoint.x, mCenterPoint.y - radius);
        float startEndY = (float) (mCenterPoint.y + radius * Math.cos(30d * Math.PI / 180d));
        mStartPoint.set(mCenterPoint.x - mSideLength * 0.5f, startEndY);
        mEndPoint.set(mCenterPoint.x + mSideLength * 0.5f, startEndY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(mRotateAngle);


        mPaint.setColor(Color.parseColor("#969696"));
        mTrianglePath.reset();
        mTrianglePath.moveTo(mStartPoint.x, mStartPoint.y);
        mTrianglePath.lineTo(mStartPoint.x + (mEndPoint.x - mStartPoint.x) * 1.0f, mStartPoint.y + (mEndPoint.y - mStartPoint.y) * 1.0f);
        mTrianglePath.lineTo(mStartPoint.x + (mThirdPoint.x - mStartPoint.x) * 1.0f, mStartPoint.y + (mThirdPoint.y - mStartPoint.y) * 1.0f);
        mTrianglePath.close();

        canvas.drawPath(mTrianglePath, mPaint);

        mPaint.setColor(mBackgroundColorColor);
        calculateTrianglePath();
        canvas.drawPath(mTrianglePath, mPaint);

        canvas.restore();
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }

    public void setAdd(boolean add) {
        mIsAdd = add;
    }

    public void setProgress(float progress, boolean add) {
        setProgress(progress);
        setAdd(add);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void calculateTrianglePath() {
        mTrianglePath.reset();
        if (mIsAdd) {
            mTrianglePath.moveTo(mStartPoint.x, mStartPoint.y);
            mTrianglePath.lineTo(mStartPoint.x + (mEndPoint.x - mStartPoint.x) * mProgress, mStartPoint.y + (mEndPoint.y - mStartPoint.y) * mProgress);
            mTrianglePath.lineTo(mStartPoint.x + (mThirdPoint.x - mStartPoint.x) * mProgress, mStartPoint.y + (mThirdPoint.y - mStartPoint.y) * mProgress);
            mTrianglePath.close();
        } else {
            mTrianglePath.moveTo(mEndPoint.x, mEndPoint.y);
            mTrianglePath.lineTo(mEndPoint.x + (mStartPoint.x - mEndPoint.x) * mProgress, mEndPoint.y + (mStartPoint.y - mEndPoint.y) * mProgress);
            mTrianglePath.lineTo(mEndPoint.x + (mThirdPoint.x - mEndPoint.x) * mProgress, mEndPoint.y + (mThirdPoint.y - mEndPoint.y) * mProgress);
            mTrianglePath.close();
        }
    }
}
