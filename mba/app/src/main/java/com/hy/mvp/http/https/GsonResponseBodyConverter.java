package com.hy.mvp.http.https;

import android.util.Log;

import com.google.gson.Gson;
import com.hy.mvp.ui.bean.RegionData;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 作者：hanyin on 2017/8/9 13:25
 * 邮箱：hanyinit@163.com
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
            Log.d("Network", "response>>" + response);
           RegionData regionData = gson.fromJson(response, RegionData.class);
            return gson.fromJson(response, type);
    }
}
