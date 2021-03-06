package com.casinodivenezia.paintcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.casinodivenezia.cvg.R;

/**
 * TODO: document your custom view class.
 */
public class IconaQuando extends View {


    public IconaQuando(Context context) {
        super(context);
        init(null, 0);
    }

    public IconaQuando(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public IconaQuando(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.IconaQuando, defStyle, 0);

        a.recycle();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        StyleKit.drawIconaQuando(canvas, new RectF(0,0, getWidth(), getHeight()), StyleKit.oro);
    }


}
