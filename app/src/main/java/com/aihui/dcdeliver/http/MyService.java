package com.aihui.dcdeliver.http;


import com.aihui.dcdeliver.bean.LoginBean;
import com.aihui.dcdeliver.bean.RecordInfoBean;
import com.aihui.dcdeliver.bean.RecordListBean;
import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.bean.TaskDetailBean;
import com.aihui.dcdeliver.bean.TaskTypeBeanList;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.http.Body;
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
    Observable<LoginBean> login(@Query("userName") String userName, @Query("password") String passWord);



    @GET("/applogout")
    Observable<ServiceBean> logout();


    /**
     * @return 测试
     */
    @GET("/app/demo")
    Observable<ServiceBean> test();


    /**
     * @return 获取任务类型Map
     */
    @GET("/app/task/getTaskClassMap")
    Observable<TaskTypeBeanList> getTasksMap();


    /**
     * @return 获取
     */
    @GET("/app/task/getTaskClassList ")
    Observable<TaskTypeBeanList> getTasksList();

    /**
     * @return 获取地理位子
     */
    @GET("/app/common/getPlaceMap")
    Observable<HashMap<String,Object>> getPlaceMap ();

    /**
     * @return 获取任务明细
     */
    @GET("/app/task/getTaskClassDetail?")
    Observable<TaskDetailBean> getTaskDetail(@Query("id") String itemId);


    /**
     * @return 获取任务明细
     */
    @Headers("Content-Type: application/json")
    @POST(" /app/task/record/saveTaskRecord?")
    Observable<ServiceBean> saveTaskRecord(@Body RequestBody route);



    /**
     * @return 获取任务明细列表
     */
    @GET("/app/task/record/queryRecord?")
    Observable<RecordListBean> getRecordList(@Query("type") String type, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /*
    * 获取任务明细
    * */
    @GET("/app/task/record/getRecordInfo?")
    Observable<RecordInfoBean> getRecordInfo(@Query("recordId") int recordId);

    /*
    * 开始运送
    * */
    @GET("/app/task/record/startRecord? ")
    Observable<ServiceBean> startRecord(@Query("recordId") int recordId);


    /*
    * 取消接单
    * */
    @GET("/app/task/record/cancelReceive?")
    Observable<ServiceBean> cancelReceive(@Query("recordId") int recordId);


    /*
    * 接收工单
    * */
    @GET ("/app/task/record/receiveRecord?")
    Observable<ServiceBean> receiveRecord(@Query("recordId") int recordId);



    @GET("/app/user/signIn")
    Observable<ServiceBean> signIn(@Query("way") int recordId);
}
