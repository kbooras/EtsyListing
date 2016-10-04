package com.kbooras.etsylistings.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;

import com.kbooras.etsylistings.R;

public class SearchEditText extends EditText {

    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            clearFocus();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        if(event.getAction() == MotionEvent.ACTION_UP &&
                event.getX() <= getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()) {
            setText("");
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void showCancelButton(boolean show) {
        int drawableLeft;
        if (show) {
            drawableLeft = R.drawable.ic_cancel;
        } else {
            drawableLeft = R.drawable.ic_magnifying_glass;
        }
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        showCancelButton(focused && getText().length() > 0);
        if (focused) {
            setBackgroundResource(R.drawable.rounded_edittext_focused);
        } else {
           setBackgroundResource(R.drawable.rounded_edittext);
        }
    }

}
