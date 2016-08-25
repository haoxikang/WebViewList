package com.example.webviewscroll;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by 康颢曦 on 2016/8/23.
 */

public class ScrollWebView extends WebView {
    boolean mIgnoreTouchCancel;
    public ScrollInterface mScrollInterface;
    private boolean isScroll = true;
    private int maxH;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void ignoreTouchCancel(boolean val) {
        mIgnoreTouchCancel = val;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
        }

        return action == MotionEvent.ACTION_CANCEL && mIgnoreTouchCancel || super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollInterface!=null){
            mScrollInterface.onSChanged(l, t, oldl, oldt);

        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
            Log.d("onSizeChanged","h="+h);
        if (h > maxH) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = maxH;
            setLayoutParams(layoutParams);
            isScroll = true;
        }
        if (h < maxH) {
            isScroll = false;
        }
        super.onSizeChanged(w, h, ow, oh);
    }

    public void setOnCustomScroolChangeListener(ScrollInterface scrollInterface) {

        this.mScrollInterface = scrollInterface;

    }

    public interface ScrollInterface {

         void onSChanged(int l, int t, int oldl, int oldt);

    }

    public void setMaxH(int h) {
        Log.d("maxH=",h+"");
        maxH = h;
    }

    public boolean isScroll() {
        return isScroll;
    }
}
