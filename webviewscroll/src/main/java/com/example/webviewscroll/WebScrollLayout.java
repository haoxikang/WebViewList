package com.example.webviewscroll;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * Created by 康颢曦 on 2016/8/23.
 */

public class WebScrollLayout extends LinearLayout {
    private ScrollWebView mDispatchWebView;
    float y1 = 0;
    float y2 = 0;
    public RecyclerviewScrollBottom recyclerviewScrollBottomListener;
    private boolean isIntercept = false;
    boolean isScrollUp = true;
    private RecyclerView recyclerView;

    public void preventParentTouchEvent(WebView view) {

        if (getChildAt(0) != null) {
            recyclerView = (RecyclerView) getChildAt(0);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                boolean isSlidingToLast = false;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (manager.findFirstCompletelyVisibleItemPosition() == 0) {
                        Log.d("TAG", "滑动到顶部");
                        onScrollTop(true);
                    } else {
                        Log.d("TAG", "滑动到不是顶部");
                        onScrollTop(false);
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的ItemPosition
                        int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();

                        // 判断是否滚动到底部，并且是向右滚动
                        if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                            recyclerviewScrollBottomListener.onScrollBottom();
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                    if (dy > 0) {
                        //大于0表示，正在向右滚动
                        isSlidingToLast = true;
                    } else {
                        //小于等于0 表示停止或向左滚动
                        isSlidingToLast = false;
                    }


                }
            });
        }


        mDispatchWebView = (ScrollWebView) view;
        if (mDispatchWebView != null) {

            isIntercept = true;
            mDispatchWebView.setOnCustomScroolChangeListener(new ScrollWebView.ScrollInterface() {
                @Override
                public void onSChanged(int l, int t, int oldl, int oldt) {
                    Log.d("调用滑动事件",l+" "+t+" "+oldl+" "+oldt);
                    float webViewContentHeight = mDispatchWebView.getContentHeight() * mDispatchWebView.getScale();
                    Log.d("内容高度",webViewContentHeight+"");
                    float webViewCurrentHeight = (mDispatchWebView.getHeight() + mDispatchWebView.getScrollY());
                    Log.d("偏移量+页面高度",webViewCurrentHeight+"");
                    if ((webViewContentHeight - webViewCurrentHeight) <=5) {
                        System.out.println("WebView滑动到了底端");
                        if (isScrollUp && mDispatchWebView.isScroll()) {
                            mDispatchWebView.ignoreTouchCancel(false);
                            isIntercept = false;
                        }

                    }else {
                        System.out.println("WebView滑动到了不是底端");
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

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
        if (mDispatchWebView!=null){
            Log.d("Log", mDispatchWebView.getHeight() + "");
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                y2 = ev.getY();
                if (y1 - y2 >ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                        isScrollUp = true;

                }
                if (y2 - y1 > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {

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
        Log.d("TAG","进入onscrolltop");
        if (mDispatchWebView != null) {

            if (!isScrollUp && istop && mDispatchWebView.isScroll()) {
                Log.d("TAG","进入onscrolltop的if");
                mDispatchWebView.ignoreTouchCancel(true);
                isIntercept = true;
            }else if (mDispatchWebView!=null){
                Log.d("TAG","进入onscrolltop的else");
                mDispatchWebView.ignoreTouchCancel(false);
                isIntercept = false;
            }
        }
    }
    public void setRecyclerviewScrollBottomListener(RecyclerviewScrollBottom recyclerviewScrollBottomListener) {

        this.recyclerviewScrollBottomListener = recyclerviewScrollBottomListener;

    }
    public interface RecyclerviewScrollBottom {

         void onScrollBottom();

    }
    public void scrollTop(){
        if (recyclerView!=null&&mDispatchWebView!=null){
            recyclerView.scrollToPosition(0);
            mDispatchWebView.scrollTo(0, 0);
            if (mDispatchWebView.isScroll()){
                mDispatchWebView.ignoreTouchCancel(true);
                isIntercept = true;
            }
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d("销毁","销毁");
        super.onDetachedFromWindow();
        mDispatchWebView.destroy();
        mDispatchWebView=null;
        recyclerView=null;
    }
}
