package com.example.webviewscroll;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by 康颢曦 on 2016/8/24.
 */

public class WebViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private int TPYE_WEB_VIEW=10;
    private RecyclerView.Adapter mInnerAdapter;
    private WebScrollLayout layout;
    private ScrollWebView scrollWebView;
    public WebViewAdapter(RecyclerView.Adapter adapter ,ScrollWebView scrollWebView)
    {
        this.scrollWebView = scrollWebView;
        mInnerAdapter = adapter;

    }

public void attachLayout(WebScrollLayout layout){
    this.layout=layout;
}
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
            if (viewType==TPYE_WEB_VIEW)
            {
                WebViewHolder holder = new WebViewHolder(scrollWebView);
                holder.webView.setMaxH(parent.getHeight());
                Log.d("Log"," "+holder.webView.getContentHeight());
                ViewGroup.LayoutParams layoutParams =new  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                holder.webView. setLayoutParams(layoutParams);
                layout.preventParentTouchEvent(holder.webView);
                return holder;

            }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position==0)
        {
            return TPYE_WEB_VIEW;
        }
        return mInnerAdapter.getItemViewType(position - 1);
    }

    private int getRealItemCount()
    {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (position==0)
        {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - 1);
    }

    @Override
    public int getItemCount()
    {
        return 1+getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                int viewType = getItemViewType(position);
                if (viewType==TPYE_WEB_VIEW)
                {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (position==0)
        {
            WrapperUtils.setFullSpan(holder);
        }
    }
    class WebViewHolder extends RecyclerView.ViewHolder {
        ScrollWebView webView ;
        public WebViewHolder(View itemView) {
            super(itemView);
            webView =(ScrollWebView)itemView;
        }
    }



}