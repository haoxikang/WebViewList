package com.example.administrator.webviewlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.webviewscroll.WebScrollLayout;
import com.example.webviewscroll.WebViewAdapter;

public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;
    private WebScrollLayout webScrollLayout;
    private  LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webScrollLayout = (WebScrollLayout)findViewById(R.id.my_layout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        WebViewAdapter webViewAdapter = new WebViewAdapter(new MyAdapter(),"http://wap.4c.cn");
        webScrollLayout.setRecyclerviewScrollBottomListener(new WebScrollLayout.RecyclerviewScrollBottom() {
            @Override
            public void onScrollBottom() {
                Log.d("测试","滑动到底部");
            }
        });
        webViewAdapter.attchLayout(webScrollLayout);
        recyclerView.setAdapter(webViewAdapter);

    }


}
