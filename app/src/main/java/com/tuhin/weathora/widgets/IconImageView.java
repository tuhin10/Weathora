package com.tuhin.weathora.widgets;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class IconImageView extends AppCompatImageView {
    public IconImageView(Context context) {
        super(context);
    }

    public IconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initColor(int color) {
        setColorFilter(color, PorterDuff.Mode.SRC_IN);
        invalidate();
    }
}
