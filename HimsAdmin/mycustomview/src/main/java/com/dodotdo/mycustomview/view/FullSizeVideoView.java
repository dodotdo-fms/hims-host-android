package com.dodotdo.mycustomview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Created by Omjoon on 16. 2. 1..
 */
public class FullSizeVideoView extends VideoView {
    public FullSizeVideoView(Context context) {
        super(context);
        init(context);
    }

    public FullSizeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FullSizeVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;
        setMeasuredDimension(deviceWidth, deviceHeight);
    }
}