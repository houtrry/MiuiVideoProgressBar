package com.houtrry.miuivideoprogressbar.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

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

public class TriangleView1 {

    private PointF startPoint;
    private PointF endPoint;
    private PointF thirdPoint;
    private Path mPath = new Path();

    private int color;

    public TriangleView1() {
    }

    public TriangleView1(PointF startPoint, PointF endPoint, PointF thirdPoint, int color) {
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

    public void show( Canvas canvas, Paint paint, float progress, boolean isReverse) {
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
        return "TriangleView1{" +
                "startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", thirdPoint=" + thirdPoint +
                ", mPath=" + mPath +
                ", color=" + color +
                '}';
    }
}
