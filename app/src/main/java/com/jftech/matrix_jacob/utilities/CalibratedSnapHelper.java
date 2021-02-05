package com.jftech.matrix_jacob.utilities;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CalibratedSnapHelper extends PagerSnapHelper
{
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager)
    {
        if(layoutManager instanceof LinearLayoutManager)
        {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if(!checkIfSnapRequired(linearLayoutManager))
                return null;
        }
        return super.findSnapView(layoutManager);
    }

    public boolean checkIfSnapRequired(LinearLayoutManager linearLayoutManager)
    {
        return (linearLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) && (linearLayoutManager.findLastCompletelyVisibleItemPosition() != linearLayoutManager.getItemCount() - 1);
    }
}
