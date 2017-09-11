package com.houtrry.miuivideoprogressbar.evaluator;


import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by houtrry on 2017/9/11.
 */

public class PointEvaluator implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return new PointF(startValue.x + (endValue.x - startValue.x) * fraction,
                startValue.y + (endValue.y - startValue.y) * fraction);
    }
}
