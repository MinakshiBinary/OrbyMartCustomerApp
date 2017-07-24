package com.binaryic.customerapp.orbymart.Custom;

import android.support.v4.view.ViewPager;

/**
 * Created by User on 08-02-16.
 */
public interface PageIndicator extends ViewPager.OnPageChangeListener{
    void setViewPager(ViewPager view);
    void setViewPager(ViewPager view, int initialPosition);
    void setCurrentItem(int item);
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);
    void notifyDataSetChanged();
}
