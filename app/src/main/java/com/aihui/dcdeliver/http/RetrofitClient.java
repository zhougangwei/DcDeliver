package com.aihui.dcdeliver.http;

import com.google.gson.Gson;

import com.aihui.dcdeliver.application.BaseApplication;
import com.aihui.dcdeliver.base.Content;
import com.aihui.dcdeliver.util.GsonUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * Created by gaohailong on 2016/5/17.
 */
public class RetrofitClient {

    private static Retrofit retrofit;
    private static Retrofit stringRetrofit;


    /**
     * 获取到的数据是对象
     * @return
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitClient.class) {
                Gson gson = GsonUtil.getGson();
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl( Content.WS_ADDRESS)
                            .addConverterFactory(CustomGsonConverterFactory.create(gson))
                            // .addConverterFactory(ToStringConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(BaseApplication.defaultOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 获取到的数据是String
     * @return
     */
    public static Retrofit getStringRetrofit() {
        if (stringRetrofit == null) {
            synchronized (RetrofitClient.class) {
                if (stringRetrofit == null) {
                    stringRetrofit = new Retrofit.Builder()
                            .baseUrl(Content.WS_ADDRESS)
                            .addConverterFactory(ToStringConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(BaseApplication.defaultOkHttpClient())
                            .build();
                }
            }
        }
        return stringRetrofit;
    }



}
