package com.hy.mvp.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hy.mvp.R;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.webview.BackHandledFragment;

import butterknife.BindView;


/**
 * 作者：hanyin on 2017/8/4 12:59
 * 邮箱：hanyinit@163.com
 */

//发现
public class DiscoverFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String url = "http://192.168.31.128:8080/mbaport/blogpost/tobrowseBlogPost?blogPostId=ac6c9c7a9264409fbcaa6f6e44645848";
    private String mParam1;
    private String mParam2;
    @BindView(R.id.webView)
    WebView webView;
    private WebSettings webSettings;
    public DiscoverFragment() {
    }


    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void loadData() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(this,"mjs");
        webView.loadUrl(url);
    }
    @JavascriptInterface
    public String getUserId(){
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        return userId;
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //    return super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            //     super.onReceivedError(view, errorCode, description, failingUrl);
            //  Toast.makeText(this,"网页加载错误！",0).show();
        }
    }
   /* @Override
    public  boolean onBackPressed(){

        if(webView.canGoBack()){
            webView.goBack();
            Log.v("webView.goBack()", "webView.goBack()");
            return true;

        }else{
            Log.v("Conversatio退出","Conversatio退出");
            return false;
        }

    }*/

}
