package com.binaryic.customerapp.orbymart.Custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.R;


/**
 * Created by Asd on 14-10-2016.
 */
public class AnimatedAutoEditText extends RelativeLayout{
    String hint = "";
    int input = 1;
    public AutoCompleteTextView et;
    public TextView txt;
    RelativeLayout lay;

    public AnimatedAutoEditText(Context context) {
        super(context);
    }

    public AnimatedAutoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }

    public AnimatedAutoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.animatedEditText, 0, 0);
        hint = a.getString(R.styleable.animatedEditText_hint_text);
        input = a.getInteger(R.styleable.animatedEditText_android_inputType, 1);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(LayoutInflater.from(getContext()).inflate(R.layout.animated_autocomplete, this, false));
        lay = (RelativeLayout) findViewById(R.id.lay);
        txt = ((TextView) findViewById(R.id.tvanim_01));
        if (txt != null) {
            txt.setText(hint);
        }
        et = ((AutoCompleteTextView) findViewById(R.id.etanim_01));

        et.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "pangram_regular.otf"), 0);
        if (et != null) {
            et.setInputType(input);
        }
        et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (et.getText().toString().length() == 0) {
                        SetAnimation(txt);
                    }
                } else {
                    if (et.getText().toString().length() == 0) {
                        SetAnimationReverse(txt);
                    }
                }
            }
        });
    }
    public void SetAnimation(View view) {
        TranslateAnimation transAnim = new TranslateAnimation(0, 0, 0, -(getDisplayHeight()/2));
        //transAnim.setStartOffset(500);
        transAnim.setDuration(300);
        transAnim.setFillAfter(true);
        transAnim.setInterpolator(new OvershootInterpolator(1.0F));
        view.startAnimation(transAnim);
    }
    private void SetAnimationReverse(View view) {
        TranslateAnimation transAnim = new TranslateAnimation(0, 0, -(getDisplayHeight()/2), 0);
        //transAnim.setStartOffset(500);
        transAnim.setDuration(300);
        transAnim.setFillAfter(true);
        transAnim.setInterpolator(new OvershootInterpolator(1.0F));
        view.startAnimation(transAnim);
    }
    private int getDisplayHeight() {
        return (int) (45 * Resources.getSystem().getDisplayMetrics().density);
    }
    public void SetError(String error){
        txt.setText(error);
        lay.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
    }
    public void RemoveError(){
        txt.setText(hint);
        lay.setBackground(getResources().getDrawable(R.drawable.rounded_border));
    }
    public AnimatedAutoEditText getInstatnce(){
        return this;
    }
}
