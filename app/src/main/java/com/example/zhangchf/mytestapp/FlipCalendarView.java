package com.example.zhangchf.mytestapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhangchf on 28/02/2017.
 *
 * To enable animation, this View will set its parent's clipChildren property to false.
 * If it doesn't work, please set its parent's clipChildren property to false manually.
 */

public class FlipCalendarView extends FrameLayout {

    private static final int FLIP_DURATION = 1000;

    ViewGroup layoutUpperPart;
    ViewGroup layoutLowerPart;
    TextView tvText;

    ImageView imgNewUpper, imgNewLower;

    Interpolator upperInterpolator, lowerInterpolator;
    int animationDuration = FLIP_DURATION / 2;

    public FlipCalendarView(Context context) {
        this(context, null);
    }

    public FlipCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_flip_calendar, this);
        setClipChildren(false);

        layoutUpperPart = (ViewGroup) findViewById(R.id.layout_upper_part);
        layoutLowerPart = (ViewGroup) findViewById(R.id.layout_lower_part);
        tvText = (TextView) findViewById(R.id.tv_text);

        upperInterpolator = new AccelerateInterpolator();
        lowerInterpolator = new DecelerateInterpolator();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    public void setAnimationDuration(int duration) {
        animationDuration = duration / 2;
    }

    public void animateTo(String newText) {
        int w = getWidth();
        int h = getHeight();

        tvText.setText(newText);
        tvText.setVisibility(VISIBLE);
        layoutUpperPart.setVisibility(INVISIBLE);
        layoutLowerPart.setVisibility(INVISIBLE);
        Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        tvText.setVisibility(INVISIBLE);
        layoutUpperPart.setVisibility(VISIBLE);
        layoutLowerPart.setVisibility(VISIBLE);

        Bitmap bmpUpper = Bitmap.createBitmap(b, 0, 0, w, h/2);
        Bitmap bmpLower = Bitmap.createBitmap(b, 0, h/2, w, h/2);

        imgNewUpper = new ImageView(getContext());
        imgNewUpper.setImageBitmap(bmpUpper);

        imgNewLower = new ImageView(getContext());
        imgNewLower.setImageBitmap(bmpLower);

        if (layoutUpperPart.getChildCount() == 0 || layoutLowerPart.getChildCount() == 0) {
            layoutUpperPart.addView(imgNewUpper, 0);
            layoutLowerPart.addView(imgNewLower);
        } else {
            animateUpper();
        }
    }

    private void animateUpper() {
        final View oldUpper = layoutUpperPart.getChildAt(0);
        layoutUpperPart.addView(imgNewUpper, 0);

        Flip3DAnimation flipAnimation = new Flip3DAnimation(0, -90, 0, oldUpper.getHeight());
        flipAnimation.setDuration(animationDuration);
        flipAnimation.setInterpolator(upperInterpolator);
        flipAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutUpperPart.removeView(oldUpper);
                animateLower();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        oldUpper.startAnimation(flipAnimation);
    }

    private void animateLower() {
        final View oldLower = layoutLowerPart.getChildAt(0);
        layoutLowerPart.addView(imgNewLower);

        Flip3DAnimation flipAnimation = new Flip3DAnimation(90, 0, 0, 0);
        flipAnimation.setDuration(animationDuration);
        flipAnimation.setInterpolator(lowerInterpolator);
        flipAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutLowerPart.removeView(oldLower);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imgNewLower.setCameraDistance(getWidth() * 10);
        imgNewLower.startAnimation(flipAnimation);
    }

}
