package com.mysafetynet.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.mysafetynet.R;

public class MySafetyButton extends AppCompatButton {
    private Typeface mTypeface;

    public MySafetyButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MySafetyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MySafetyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MySafetyButton);
            String fontName = a.getString(R.styleable.MySafetyButton_fontButtonName);
            if (fontName == null && TextUtils.isEmpty(fontName)) {
                fontName = "Raleway Light.ttf";
            }
            mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            setTypeface(mTypeface);
            a.recycle();
        }

    }
}
