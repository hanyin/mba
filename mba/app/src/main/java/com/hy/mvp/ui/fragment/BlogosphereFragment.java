package com.hy.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.hy.mvp.R;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.ui.activity.MainActivity;
import com.hy.mvp.ui.activity.PostContentActivity;
import com.hy.mvp.ui.activity.SearchContentActivity;
import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.webview.BackHandledFragment;
import com.hy.mvp.ui.webview.BaseWebViewFragment;
import com.hy.mvp.utils.CommonUtil;
import com.hy.mvp.view.PopupItem;
import com.hy.mvp.view.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：hanyin on 2017/8/8 11:53
 * 邮箱：hanyinit@163.com
 */

/**
 * 部落圈
 */
public class BlogosphereFragment  extends BaseFragment implements View.OnClickListener{
    public static final String TAG = BlogosphereFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
     private String url = ApiRetrofit.BLOGPOST;
    private String mParam1;
    private String mParam2;
    private String mParam3;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.rlMore)
    RelativeLayout rlMore;
    @BindView(R.id.rlTitle)
    RelativeLayout rlTitle;
    MainActivity mainActivity;
    private WebSettings webSettings;
    public BlogosphereFragment() {
    }

    public static BlogosphereFragment newInstance(String param1, String param2, String param3) {
        BlogosphereFragment fragment = new BlogosphereFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mParam1 = "";
        mParam2 = "";
        mParam3 = "";
    }

    @Override
    public void onAttach(Activity activity) {
        mainActivity = (MainActivity) activity;
        super.onAttach(activity);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_blogosphere;
    }
    @Override
    public void initListener() {
        super.initListener();
        rlMore.setOnClickListener(this);
        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }

        });
    }
    @Override
    protected void loadData() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(this,"mjs");
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        url =  url+"?query_typeId="+ mParam1+"&query_content="+mParam2+"&query_realname="+mParam3+"&user_id="+userId;
        webView.loadUrl(url);

        Log.i(TAG, "loadData: "+url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
        }
    }

    @JavascriptInterface
    public String getUserId(){
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
       return userId;
    }
    @JavascriptInterface
    public void pageToBlogPostDetail(String blogpostId){
        CommonUtil.showToast(blogpostId);
        Log.i("blogpostId",blogpostId);
    }

   @Override
   public void onClick(View v) {
       switch (v.getId()){
           case R.id.rlMore:
               showMore();
               break;
       }
   }

    private void showMore() {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), rlTitle);
        List<PopupItem> items = new ArrayList<>();
        PopupItem pop1 = new PopupItem();
        pop1.setContent("发布");
        pop1.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop1);
        PopupItem pop2 = new PopupItem();
        pop2.setContent("搜索");
        pop2.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop2);

        PopupItem pop3 = new PopupItem();
        pop3.setContent("我的帖子");
        pop3.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop3);

        PopupItem pop4 = new PopupItem();
        pop4.setContent("我的评论");
        pop4.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop4);

        PopupItem pop5 = new PopupItem();
        pop5.setContent("我的点赞");
        pop5.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop5);

        PopupItem pop6 = new PopupItem();
        pop6.setContent("草稿箱");
        pop6.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop6);

        popupMenu.setDataChanged(items);
        popupMenu.setOnPopupItemClickListener(new PopupMenu.OnPopupItemClickListener() {
            @Override
            public void onItemClicked(int i, PopupItem popupItem) {
                Intent intent = new Intent();
                if(i==0){
                    //发布
                    intent.setClass(getActivity(),PostContentActivity.class);
                    startActivity(intent);
                }else if(i==1){
                    //搜索
                    intent.setClass(getActivity(), SearchContentActivity.class);
                    startActivity(intent);
                }else if(i==2){
                    //我的帖子
                    CommonUtil.showToast("开发中...");
                }else if(i==3){
                    //我的评论
                    CommonUtil.showToast("开发中...");
                }else if(i==4){
                    //我的点赞
                    CommonUtil.showToast("开发中...");
                }else if(i==5){
                    //草稿箱
                    CommonUtil.showToast("开发中...");
                }

                popupMenu.hide();
            }
        });
        popupMenu.setPopupMenuWidth(140);
        popupMenu.show();
    }
}
