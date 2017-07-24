package com.binaryic.customerapp.orbymart.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewCollection extends ImageView {
    public ImageViewCollection(Context paramContext) {
        super(paramContext);
    }

    public ImageViewCollection(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public ImageViewCollection(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {

        super.onMeasure(paramInt1, paramInt2);
        int i = getMeasuredWidth();
        if (i == getMeasuredHeight()) {
            setMeasuredDimension(i, i);
            return;
        }
        setMeasuredDimension(i, i / 2);
    }
}