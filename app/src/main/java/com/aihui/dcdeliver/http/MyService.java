package com.aihui.dcdeliver.http;


import com.aihui.dcdeliver.bean.ServiceBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gaohailong on 2016/5/17.
 */
public interface MyService {
    /**
     * @param userName 用户
     * @param passWord 密码
     * @return
     */
    @GET("/applogin?")
    Observable<ServiceBean> login(@Query("userName") String userName, @Query("password") String passWord);

    /**
     * @return 测试
     */
    @GET("/app/demo")
    Observable<ServiceBean> test();

}
