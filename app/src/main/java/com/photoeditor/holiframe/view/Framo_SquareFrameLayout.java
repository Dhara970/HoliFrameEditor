package com.photoeditor.holiframe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class Framo_SquareFrameLayout extends FrameLayout {
    public Framo_SquareFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int requestCode, int  resultCode) {
        super.onMeasure(requestCode, requestCode);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth <= measuredHeight) {
            setMeasuredDimension(measuredWidth, measuredWidth);
        } else {
            setMeasuredDimension(measuredHeight, measuredHeight);
        }
    }

}
