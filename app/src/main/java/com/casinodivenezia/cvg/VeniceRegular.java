package com.casinodivenezia.cvg;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class VeniceRegular extends TextView {
    public VeniceRegular(Context context) {
        super(context);
        setFont();
    }
    public VeniceRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public VeniceRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/venicecasinoregular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
