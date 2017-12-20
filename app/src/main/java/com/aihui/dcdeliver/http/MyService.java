package com.aihui.dcdeliver.http;


import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.bean.TaskTypeBeanList;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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


    /**
     * @return 获取任务类型Map
     */
    @GET("/app/task/getTaskClassMap")
    Observable<ServiceBean> getTasksMap();


    /**
     * @return 获取
     */
    @GET("/app/task/getTaskClassList ")
    Observable<TaskTypeBeanList> getTasksList();

    /**
     * @return 获取地理位子
     */
    @GET("/app/task/getPlaceMap ")
    Observable<ServiceBean> getPlaceMap ();

    /**
     * @return 获取任务明细
     */
    @GET("/app/task/getTaskClassDetail?")
    Observable<ServiceBean> getTaskDetail(@Query("id") String itemId);


    /**
     * @return 获取任务明细
     */
    @Headers("Content-Type: application/json")
    @POST(" /app/task/record/saveTaskRecord?")
    Observable<ServiceBean> saveTaskRecord(@Query("id") String itemId);

}
