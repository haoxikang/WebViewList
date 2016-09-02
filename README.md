# WebViewList
[![](https://jitpack.io/v/348476129/WebViewList.svg)](https://jitpack.io/#348476129/WebViewList)
![image](https://github.com/348476129/WebViewList/blob/master/gif5新文件.gif)
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
	        compile 'com.github.348476129:WebViewList:1.1.5'
	}
	

使用方法：

第一步：

用 WebScrollLayout 包裹 RecyclerView 代替 RecyclerView

第二步：

在activity中：
   
    private RecyclerView recyclerView;
    private WebScrollLayout webScrollLayout;
    
    
      webScrollLayout = (WebScrollLayout)findViewById(R.id.my_layout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
          ScrollWebView scrollWebView = new ScrollWebView(this);
        scrollWebView.loadUrl(url);
        WebViewAdapter webViewAdapter = new WebViewAdapter(new MyAdapter(),scrollWebView);
        webScrollLayout.setRecyclerviewScrollBottomListener(new WebScrollLayout.RecyclerviewScrollBottom() {
            @Override
            public void onScrollBottom() {
                //滑动到了底部监听
            }
        });
        webViewAdapter.attchLayout(webScrollLayout);
        recyclerView.setAdapter(webViewAdapter);
    
    
