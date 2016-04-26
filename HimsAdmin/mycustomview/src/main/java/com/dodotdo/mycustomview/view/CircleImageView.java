package com.dodotdo.mycustomview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;


/**
 * Created by Minchul on 2015-01-29.
 */
public class CircleImageView extends RoundedImageView {
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOval(true);
        this.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        if (height > width) {
            setMeasuredDimension(width, width);
        } else {
            setMeasuredDimension(height, height);
        }
    }
}
