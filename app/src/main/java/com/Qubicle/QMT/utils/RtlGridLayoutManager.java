package com.Qubicle.QMT.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Created by Anju on 04-09-2019.
 */
public class RtlGridLayoutManager extends GridLayoutManager {

    public RtlGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RtlGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public RtlGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    protected boolean isLayoutRTL(){
        return true;
    }
}