package com.hy.mvp.http.https;


import android.support.annotation.NonNull;
import android.util.Log;

import com.hy.mvp.app.MyApplication;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.bean.LinkFile;
import com.hy.mvp.ui.bean.RegionData;
import com.hy.mvp.ui.bean.RegionDataList;
import com.hy.mvp.ui.bean.YearSalary;
import com.hy.mvp.utils.NetWorkUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * 作者：hanyin on 2017/8/9 13:25
 * 邮箱：hanyinit@163.com
 */
public class HttpMethods {

   public static final String BASE_SERVER_URL = "http://192.168.31.150:8080/mbaport/";
    // public static final String BASE_SERVER_URL = "http://192.168.0.102:8080/mbaport/";
    // public static final String BASE_SERVER_URL = "http://192.168.31.128:8080/mbaport/";
    private static final int DEFAULT_TIMEOUT = 20;
    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    private Retrofit retrofit;
    private ApiService apiService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(MyApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
      // OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        //builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                 .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_SERVER_URL)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取城市
     * @param subscriber
     */
    public void getRegionList(Subscriber<RegionData<RegionDataList>> subscriber){
       /* Observable observable = apiService.getRegionList()
                .map(new RegionDataFunc<RegionDataList>());
        toSubscribe(observable, subscriber);*/
    }
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
         o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            Log.i("RetrofitLog", "retrofitBack = " + message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class RegionDataFunc<T> implements Func1<RegionData<T>, T>{

        @Override
        public T call(RegionData<T> regionData) {

            return regionData.getRespData();
        }
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetworkAvailable(MyApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetWorkUtils.isNetworkAvailable(MyApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };
    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }
    /**
     * 获取年薪
     * @param subscriber
     */
    public void getYearSalary(Subscriber<YearSalary<YearSalary.RespDataBean>> subscriber){
        Observable observable = apiService.getYearSalary()
                .map(new YearSalaryFunc<YearSalary.RespDataBean>());
        toSubscribe(observable, subscriber);
    }

    private class YearSalaryFunc<T> implements Func1<YearSalary<T>, T>{

        @Override
        public T call(YearSalary<T> regionData) {

            return regionData.getRespData();
        }
    }


    private class FileBeanFunc<T> implements Func1<LinkFile<T>, T>{

        @Override
        public T call(LinkFile<T> linkFile) {

            return linkFile.getFile();
        }
    }
}
