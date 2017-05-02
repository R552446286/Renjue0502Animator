
package com.baway.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public void xuanzhuan(View view){
        ObjectAnimator.ofFloat(iv,"rotationX",0.0f,360.0f).setDuration(3000).start();
    }
    public void zuhe(View view){
        ObjectAnimator anim=ObjectAnimator.ofFloat(iv,"rj",1.0f,0.0f).setDuration(500);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal= (float) animation.getAnimatedValue();
                iv.setAlpha(cVal);
                iv.setScaleX(cVal);
                iv.setScaleY(cVal);
            }
        });
    }
    public void chuizhi(View view){
        ValueAnimator animator=ValueAnimator.ofFloat(0,height-iv.getHeight());
        animator.setTarget(iv);
        animator.setDuration(1000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                iv.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }
    public void paowuxian(View view){
        ValueAnimator valueAnimator=new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0,0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                pointF.x=200*fraction*3;
                pointF.y=0.5f*200*(fraction*3)*(fraction*3);
                return pointF;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF= (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);
            }
        });
    }
    public void danchushanchu(View view){
        ObjectAnimator anim=ObjectAnimator.ofFloat(iv,"alpha",0.5f);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup parent = (ViewGroup) iv.getParent();
                if (parent!=null){
                    parent.removeView(iv);
                }
            }
        });
        anim.start();
    }
    public void playTogether(View view){
        ObjectAnimator anim1=ObjectAnimator.ofFloat(iv,"scaleX",1.0f,2f);
        ObjectAnimator anim2=ObjectAnimator.ofFloat(iv,"scaleY",1.0f,2f);
        AnimatorSet animSet=new AnimatorSet();
        animSet.setDuration(2000);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1,anim2);
        animSet.start();
    }
    public void playAfter(View view){
        ObjectAnimator anim1=ObjectAnimator.ofFloat(iv,"scaleX",1.0f,2f);
        ObjectAnimator anim2=ObjectAnimator.ofFloat(iv,"scaleY",1.0f,2f);
        ObjectAnimator anim3=ObjectAnimator.ofFloat(iv,"x",iv.getX(),0f);
        ObjectAnimator anim4=ObjectAnimator.ofFloat(iv,"x",iv.getX());
        AnimatorSet animSet=new AnimatorSet();
        animSet.play(anim1).with(anim2);
        animSet.play(anim2).with(anim3);
        animSet.play(anim4).after(anim3);
        animSet.setDuration(1000);
        animSet.start();
    }
}
