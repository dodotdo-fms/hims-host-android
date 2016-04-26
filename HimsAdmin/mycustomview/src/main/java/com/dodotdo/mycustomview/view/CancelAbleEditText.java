package com.dodotdo.mycustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dodotdo.mycustomview.R;

/**
 * Created by Omjoon on 16. 1. 26..
 */
public class CancelAbleEditText extends LinearLayout {
    EditText editText;
    ImageView cancel;
    String textHint;
    Drawable editBackground;
    String inputType;
    View background;
    public CancelAbleEditText(Context context) {
        this(context,null);
    }

    public CancelAbleEditText(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CancelAbleEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CancelAbleEditText, defStyleAttr, 0);
        if (a == null) {
            return;
        }
        textHint = a.getString(R.styleable.CancelAbleEditText_textHint);
        editBackground = a.getDrawable(R.styleable.CancelAbleEditText_editorBackground);
        if (editBackground == null) {
            editBackground = getResources().getDrawable(R.drawable.btn_radius_white);
        }
        inputType = a.getString(R.styleable.CancelAbleEditText_textInput);
        float alpha  = a.getFloat(R.styleable.CancelAbleEditText_alpha,1);
        a.recycle();
        background.setAlpha(alpha);
        if(inputType != null && inputType.equals("password")) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        editText.setHint(textHint);
        background.setBackground(editBackground);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cancel_able_edit_text, this, true);
        editText = (EditText) view.findViewById(R.id.edt);
        cancel = (ImageView) view.findViewById(R.id.cancel);
        background = view.findViewById(R.id.background);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    cancel.setVisibility(View.VISIBLE);
                } else {
                    cancel.setVisibility(View.GONE);
                }
            }
        });
    }

    public EditText getTextView(){
        return editText;
    }
    public void setText(String text){
        editText.setText(text);
    }
    public String getText(){
        return editText.getText().toString().trim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
