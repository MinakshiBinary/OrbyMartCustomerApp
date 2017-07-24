package com.binaryic.customerapp.orbymart.Custom;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by User on 08-09-2016.
 */
public class TextViewPrimary extends TextView {
    public TextViewPrimary(Context paramContext)
    {
        super(paramContext);
        a();
    }

    public TextViewPrimary(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        a();
    }

    public TextViewPrimary(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        a();
    }

    public void a()
    {
        if (Build.VERSION.SDK_INT < 21)
        {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Regular.ttf"), 0);
            return;
        }
        int i = getTypeface().getStyle();
        if (i == 1)
        {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Regular.ttf"), i);
            return;
        }
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Regular.ttf"), 0);
    }
}
