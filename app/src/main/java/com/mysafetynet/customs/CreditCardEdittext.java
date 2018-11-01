package com.mysafetynet.customs;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.TextWatcher;
import android.util.AttributeSet;


public class CreditCardEdittext extends TextInputEditText {
    private CreditCardBaseTextWatcher mTextWatcher;
    public CreditCardEdittext(Context context) {
        super(context);
    }

    public CreditCardEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CreditCardEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public CreditCardBaseTextWatcher getTextWatcher() {
        return mTextWatcher;
    }

    public void setTextWatcher(CreditCardBaseTextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
    }

    public void setCopyPastedText(CharSequence text) {
        mTextWatcher.setIsCopyPasted(true);
        setText(text);
        mTextWatcher.setIsCopyPasted(false);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
        if(watcher instanceof CreditCardBaseTextWatcher) {
            CreditCardBaseTextWatcher creditCardBaseTextWatcher = (CreditCardBaseTextWatcher) watcher;
            setTextWatcher(creditCardBaseTextWatcher);
        }
    }
}
