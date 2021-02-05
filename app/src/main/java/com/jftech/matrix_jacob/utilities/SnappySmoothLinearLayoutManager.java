package com.jftech.matrix_jacob.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class SnappySmoothLinearLayoutManager extends LinearLayoutManager
{
    private static final float Milliseconds_Per_Inch = 50f;
    private final int parentWindowWidth;

    public SnappySmoothLinearLayoutManager(Context context, int parentWindowWidth)
    {
        super(context);
        this.parentWindowWidth = parentWindowWidth;
    }

    public SnappySmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout, int parentWindowWidth)
    {
        super(context, orientation, reverseLayout);
        this.parentWindowWidth = parentWindowWidth;
    }

    public SnappySmoothLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int parentWindowWidth)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.parentWindowWidth = parentWindowWidth;
    }

    //Auto-size the views by the screen size
    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp)
    {
        lp.width = (int)(parentWindowWidth * 0.40);
        lp.height = (int)(lp.width * 0.80);
        return true;
    }
    //Adjust the scroll speed for a smoother experience

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position)
    {
        final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext())
        {
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics)
            {
                return Milliseconds_Per_Inch / displayMetrics.densityDpi;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
