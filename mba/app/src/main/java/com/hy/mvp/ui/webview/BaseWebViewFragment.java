package com.hy.mvp.ui.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hy.mvp.R;

/**
 * 作者：hanyin on 2017/8/11 15:42
 * 邮箱：hanyinit@163.com
 */

public class BaseWebViewFragment extends BackHandledFragment {

    private WebView webView;
    private WebSettings webSettings;
    private String url = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_webview,container,false);
        webView = (WebView)view.findViewById(R.id.webView);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(getUrl());

        return view;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    @Override
    public  boolean onBackPressed(){

        if(webView.canGoBack()){
            webView.goBack();
            Log.v("webView.goBack()", "webView.goBack()");
            return true;

        }else{
            Log.v("Conversatio退出","Conversatio退出");
            return false;
        }

    }

}
