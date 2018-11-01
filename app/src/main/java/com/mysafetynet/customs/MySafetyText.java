package com.mysafetynet.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mysafetynet.R;


public class MySafetyText extends AppCompatTextView {
    private Typeface mTypeface;

    public MySafetyText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MySafetyText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MySafetyText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MySafetyText);
            String fontName = a.getString(R.styleable.MySafetyText_fontName);
            mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            setTypeface(mTypeface);
            a.recycle();
        }

    }
}
