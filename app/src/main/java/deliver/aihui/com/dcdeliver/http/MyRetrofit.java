package deliver.aihui.com.dcdeliver.http;

import com.google.gson.Gson;

import deliver.aihui.com.dcdeliver.application.BaseApplication;
import deliver.aihui.com.dcdeliver.base.Content;
import deliver.aihui.com.dcdeliver.util.GsonUtil;
import deliver.aihui.com.dcdeliver.util.SPUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static deliver.aihui.com.dcdeliver.application.BaseApplication.sContext;


/**
 * Created by gaohailong on 2016/5/17.
 */
public class MyRetrofit {

    private static Retrofit retrofit;
    private static Retrofit stringRetrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (MyRetrofit.class) {
                Gson gson = GsonUtil.getGson();
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(SPUtil.getString(sContext, Content.WS_ADDRESS, ""))
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            // .addConverterFactory(ToStringConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(BaseApplication.defaultOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static Retrofit getStringRetrofit() {
        if (stringRetrofit == null) {
            synchronized (MyRetrofit.class) {
                if (stringRetrofit == null) {
                    stringRetrofit = new Retrofit.Builder()
                            .baseUrl(SPUtil.getString(sContext, Content.WS_ADDRESS, ""))
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
