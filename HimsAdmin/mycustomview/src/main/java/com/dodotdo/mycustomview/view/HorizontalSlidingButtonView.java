package com.dodotdo.mycustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.dodotdo.mycustomview.R;


/**
 * Created by Omjoon on 16. 2. 25..
 */
public class HorizontalSlidingButtonView extends RelativeLayout{
    private ImageView leftBtn,rightBtn,centerBtn;
    private int centerPosition;
    private MultiTouchListener touchListener;
    private MultiTouchListener.OnSlideListener listener;
    public HorizontalSlidingButtonView(Context context) {
        this(context, null);
    }

    public HorizontalSlidingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalSlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalSlidingButtonView, defStyleAttr, 0);
        if (a == null) {
            return;
        }
        Drawable leftImageId = a.getDrawable(R.styleable.HorizontalSlidingButtonView_leftSrc);
        Drawable rightImageid = a.getDrawable(R.styleable.HorizontalSlidingButtonView_rightSrc);
        Drawable centerImageId = a.getDrawable(R.styleable.HorizontalSlidingButtonView_centerSrc);

        if(leftImageId != null){
            leftBtn.setImageDrawable(leftImageId);
        }
        if(rightImageid != null){
            rightBtn.setImageDrawable(rightImageid);
        }
        if(centerImageId != null){
            centerBtn.setImageDrawable(centerImageId);
        }
    }

    private void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_horizontal_sliding_button, this, true);
        leftBtn = (ImageView)view.findViewById(R.id.leftBtn);
        centerBtn = (ImageView)view.findViewById(R.id.centerBtn);
        rightBtn = (ImageView)view.findViewById(R.id.rightBtn);
        rightBtn.setEnabled(false);
        leftBtn.setEnabled(false);
        centerPosition = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(centerPosition == 0) {
            centerPosition = (int)centerBtn.getX();
        }
        touchListener=new MultiTouchListener(getContext(),centerPosition,leftBtn,rightBtn);
        touchListener.setListener(listener);
        Log.e("sdwdw","asss");
        centerBtn.setOnTouchListener(touchListener);
    }

    public void setOnSlidingListener(MultiTouchListener.OnSlideListener listener){
        this.listener = listener;
    }
}
