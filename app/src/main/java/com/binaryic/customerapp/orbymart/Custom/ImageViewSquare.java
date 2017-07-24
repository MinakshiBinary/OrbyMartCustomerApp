package com.binaryic.customerapp.orbymart.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewSquare extends ImageView
{
  public ImageViewSquare(Context paramContext)
  {
    super(paramContext);
  }

  public ImageViewSquare(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public ImageViewSquare(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = getMeasuredWidth();
    Double h = i*1.3;
    setMeasuredDimension(i, h.intValue());
  }
}

/* Location:           C:\Users\User\Downloads\Compressed\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     com.vilara.android.view.custom.ImageViewSquare
 * JD-Core Version:    0.6.0
 */