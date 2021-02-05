package com.jftech.matrix_jacob.utilities;

import android.view.View;
import android.view.ViewGroup;

public class ViewExtension
{
    public  static  void setMargins (View view, int left, int top, int right, int bottom)
    {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)
        {
            ViewGroup.MarginLayoutParams newMargin = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
            newMargin.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
