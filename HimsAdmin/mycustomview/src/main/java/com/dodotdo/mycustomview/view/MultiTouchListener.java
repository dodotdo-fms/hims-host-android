package com.dodotdo.mycustomview.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Omjoon on 16. 2. 25..
 */
public class MultiTouchListener implements View.OnTouchListener
{

    private float mPrevX;
    private Context mConext;
    private int leftLimit;
    private int rightLimit;
    private int centerPosition;
    int changeX;
    private int leftHalfWidth;
    private int rightHalfWidth;
    private View left;
    private View right;
    public interface OnSlideListener{
        void onLeftSlide();
        void onRightSlide();
    }
    private OnSlideListener listener;
    public MultiTouchListener(Context context, int centerPosition, View left, View right) {
        mConext = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.left = left;
        this.right = right;
        this.centerPosition = centerPosition;
        leftHalfWidth = (int)(left.getWidth())-Math.round(5 * dm.density);
        rightHalfWidth = (int)(right.getWidth())-Math.round(7 * dm.density);
        leftLimit = (int)(left.getX()-left.getWidth()/2)-Math.round(5 * dm.density);
        rightLimit = (int)(right.getX()-right.getWidth()/2)-Math.round(7 * dm.density);
    }

    public void setListener(OnSlideListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float currX;
        int action = event.getAction();
        switch (action ) {
            case MotionEvent.ACTION_DOWN: {
                clearFocus();
                mPrevX = event.getX();
                break;
            }

            case MotionEvent.ACTION_MOVE:
            {

                currX = event.getRawX();
                changeX = (int)(currX - mPrevX);
                View parent = (View)view.getParent();
                Log.e("changex",Math.abs(changeX)+":"+leftLimit+":"+rightLimit);
                if(changeX <= leftLimit+leftHalfWidth) {
                    changeX = leftLimit;
                    focusLeft(view);
                    return false;
                }

                if(changeX >= rightLimit-rightHalfWidth) {
                    changeX = rightLimit;
                    focusRight(view);
                    return false;
                }


                setMargins(view);


                break;
            }



            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                if(changeX > leftLimit+leftHalfWidth && changeX < rightLimit-rightHalfWidth) {
                    changeX = centerPosition;
                    setMargins(view);
                    return false;
                }



                break;
        }

        return true;
    }

    private void focusLeft(View view){
        setMargins(view);
        left.setEnabled(true);
        listener.onLeftSlide();
    }

    private void focusRight(View view){
        setMargins(view);
        right.setEnabled(true);
        listener.onRightSlide();
    }

    private void clearFocus(){
        left.setEnabled(false);
        right.setEnabled(false);
    }

    private void setMargins(View view){
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginParams.setMargins(changeX, (int)(view.getY()),0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        view.setLayoutParams(layoutParams);
    }

}

