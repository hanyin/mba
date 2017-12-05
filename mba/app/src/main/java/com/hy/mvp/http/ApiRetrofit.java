package com.hy.mvp.http;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.utils.NetWorkUtils;
import com.socks.library.KLog;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作者：hanyin on 2017/8/8 10:30
 * 邮箱：hanyinit@163.com
 */

public class ApiRetrofit {

    private static ApiRetrofit mApiRetrofit;
    private final Retrofit mRetrofit;
    private OkHttpClient mClient;
    private ApiService mApiService;
     public static final String BASE_SERVER_URL = "http://192.168.31.128:8080/mbaport/";
    //public static String BLOGPOST = BASE_SERVER_URL+"blogpost/tolistBlogPostAndroid";
    // public static final String BASE_SERVER_URL = "http://192.168.0.102:8080/mbaport/";
    // public static String BASE_SERVER_URL = "http://192.168.31.128:8080/mbaport/";
    //部落圈地址
    public static String BLOGPOST = BASE_SERVER_URL + "blogpost/tolistBlogPost";


    //缓存配置
    private Interceptor mCacheInterceptor = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetWorkUtils.isNetworkAvailable(MyApplication.getContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtils.isNetworkAvailable(MyApplication.getContext())) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

    /**请求访问request和response拦截器*/
    private Interceptor mLogInterceptor = chain -> {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        KLog.e("----------Request Start----------------");
        KLog.e("| " + request.toString());
        KLog.json("| Response:" + content);
        KLog.e("----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    };

    /**增加头部信息的拦截器*/
    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
        builder.addHeader("Cache-Control", "max-age=0");
        builder.addHeader("Upgrade-Insecure-Requests", "1");
        builder.addHeader("X-Requested-With", "XMLHttpRequest");
        builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
        return chain.proceed(builder.build());
    };


    public ApiRetrofit() {
        //cache url
        File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);


        mClient = new OkHttpClient.Builder()
                .addInterceptor(mHeaderInterceptor)//添加头部信息拦截器
                .addInterceptor(loggingInterceptor)//添加头部信息拦截器
                .addInterceptor(mLogInterceptor)//添加log拦截器
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .client(mClient)
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            Log.i("RetrofitLog", "retrofitBack = " + message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);
    public static ApiRetrofit getInstance() {
        if (mApiRetrofit == null) {
            synchronized (Object.class) {
                if (mApiRetrofit == null) {
                    mApiRetrofit = new ApiRetrofit();
                }
            }
        }
        return mApiRetrofit;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
