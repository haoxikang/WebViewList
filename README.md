# WebViewList
解决了 Webview+RecyclerView的滑动冲突。能让两个控件协同滑动。
如何导入：

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.348476129:WebViewList:0.1.2'
	}
	使用方法：
	第一步：在xml中：
	<com.example.webviewscroll.WebScrollLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/my_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.administrator.webviewlist.MainActivity">
<android.support.v7.widget.RecyclerView
    android:id="@+id/recycler"
    android:fadingEdge="none"
    android:overScrollMode="never"
    android:layout_width="match_parent"
    android:layout_height="match_parent"></android.support.v7.widget.RecyclerView>
</com.example.webviewscroll.WebScrollLayout>
第二步：在activity中：
   
    private RecyclerView recyclerView;
    private WebScrollLayout webScrollLayout;
    
    
      webScrollLayout = (WebScrollLayout)findViewById(R.id.my_layout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WebViewAdapter webViewAdapter = new WebViewAdapter(new MyAdapter(),"http://wap.4c.cn");
        webScrollLayout.setRecyclerviewScrollBottomListener(new WebScrollLayout.RecyclerviewScrollBottom() {
            @Override
            public void onScrollBottom() {
                //滑动到了底部监听
            }
        });
        webViewAdapter.attchLayout(webScrollLayout);
        recyclerView.setAdapter(webViewAdapter);
    
    
