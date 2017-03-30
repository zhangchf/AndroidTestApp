package com.example.zhangchf.mytestapp;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by zhangchf on 27/02/2017.
 */

public class Flip3DAnimation extends Animation {
    private final float fromDegree;
    private final float toDegree;
    private final float centerX;
    private final float centerY;
    private Camera camera = new Camera();

    public Flip3DAnimation(float fromDegree, float toDegree, float centerX, float centerY) {
        this.fromDegree = fromDegree;
        this.toDegree = toDegree;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degree = fromDegree + ((toDegree - fromDegree) * interpolatedTime);

        Matrix matrix = t.getMatrix();

        camera.save();
        camera.rotateX(degree);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
