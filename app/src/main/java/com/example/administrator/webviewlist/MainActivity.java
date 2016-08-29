package com.example.administrator.webviewlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.webviewscroll.ScrollWebView;
import com.example.webviewscroll.WebScrollLayout;
import com.example.webviewscroll.WebViewAdapter;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button button;
    private WebScrollLayout webScrollLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);

        webScrollLayout = (WebScrollLayout) findViewById(R.id.my_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ScrollWebView scrollWebView = new ScrollWebView(this);
        scrollWebView.loadUrl("https://app.4c.cn/web/details?nid=244&systemType=android&phoneName=E6683&phoneCode=33e129936a25fb1ac94f351e2fea6076");
        WebViewAdapter webViewAdapter = new WebViewAdapter(new MyAdapter(), scrollWebView);
        webScrollLayout.setRecyclerviewScrollBottomListener(new WebScrollLayout.RecyclerviewScrollBottom() {
            @Override
            public void onScrollBottom() {
                //滑动到了底部监听
            }
        });
        webViewAdapter.attachLayout(webScrollLayout);
        recyclerView.setAdapter(webViewAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    recyclerView.scrollToPosition(0);
                    scrollWebView.scrollTo(0, 0);


                //    recyclerView.smoothScrollToPosition(20);
            }
        });
    }
}
