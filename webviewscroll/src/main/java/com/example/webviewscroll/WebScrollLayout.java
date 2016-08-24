package com.example.webviewscroll;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * Created by 康颢曦 on 2016/8/23.
 */

public class WebScrollLayout extends LinearLayout {
    private ScrollWebView mDispatchWebView;
    float y1 = 0;
    float y2 = 0;
    private boolean isIntercept = false;
    boolean isScrollUp = true;
    private RecyclerView recyclerView;

    public void preventParentTouchEvent(WebView view) {

        if (getChildAt(0) != null) {
            recyclerView = (RecyclerView) getChildAt(0);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (manager.findFirstCompletelyVisibleItemPosition() == 0) {
                        Log.d("TAG", "滑动到顶部");
                        onScrollTop(true);
                    } else {
                        onScrollTop(false);
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                }
            });
        }


        mDispatchWebView = (ScrollWebView) view;
        if (mDispatchWebView != null) {
            isIntercept = true;
            mDispatchWebView.setOnCustomScroolChangeListener(new ScrollWebView.ScrollInterface() {
                @Override
                public void onSChanged(int l, int t, int oldl, int oldt) {
                    float webViewContentHeight = mDispatchWebView.getContentHeight() * mDispatchWebView.getScale();
                    //WebView的现高度
                    float webViewCurrentHeight = (mDispatchWebView.getHeight() + mDispatchWebView.getScrollY());
                    if ((webViewContentHeight - webViewCurrentHeight) == 0) {
                        System.out.println("WebView滑动到了底端");
                        if (isScrollUp && mDispatchWebView.isScroll()) {
                            mDispatchWebView.ignoreTouchCancel(false);
                            isIntercept = false;
                        }

                    }
                }
            });
        }

    }

    public WebScrollLayout(Context context) {
        this(context, null);
    }

    public WebScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    //手势事件拦截判断
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /**
         * 当在页面某节点的touchstart事件里调用preventHostViewTouchMove时
         * 此时拦截后续的TouchMove事件给mDispatchWebView
         * 但在这之前mDispatchWebView会收到一个Touch Cancel消息
         * 这时应该让mDispatchWebView暂时忽略这个Cancel事件（在TouchUp后恢复）
         * 避免打断页面DOM节点绑定的事件处理流程而导致节点不跟手或不响应
         * */
        if (ev.getAction() == MotionEvent.ACTION_MOVE && isIntercept) {
            {
                if (mDispatchWebView.isScroll()) {
                    mDispatchWebView.ignoreTouchCancel(true);
                    return true;
                }
            }


        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("Log", mDispatchWebView.getHeight() + "");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                y2 = ev.getY();
                if (y1 - y2 > 50) {
                    isScrollUp = true;

                }
                if (y2 - y1 > 50) {
                    isScrollUp = false;

                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isIntercept) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    mDispatchWebView.onTouchEvent(ev);
                    break;
                default:
                    mDispatchWebView.onTouchEvent(ev);
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void onScrollTop(boolean istop) {  //recyclerview是否滑动到顶部的时候
        if (mDispatchWebView != null) {

            if (!isScrollUp && istop && mDispatchWebView.isScroll()) {
                mDispatchWebView.ignoreTouchCancel(true);
                isIntercept = true;
            }
        }
    }

}
