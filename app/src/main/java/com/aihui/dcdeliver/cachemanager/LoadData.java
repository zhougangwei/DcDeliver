package com.aihui.dcdeliver.cachemanager;

import android.text.TextUtils;

import com.google.gson.Gson;


import java.lang.reflect.Type;
import java.util.List;

import com.aihui.dcdeliver.bean.CommentBean;
import com.aihui.dcdeliver.util.GsonUtil;


//可以看做一个工具类
public class LoadData {

    private LoadData () {

    }

    private static LoadData sLoadData = new LoadData();


    public static LoadData getInstance() {
        return sLoadData;
    }

    //获取json对象的方法
    public <T> T getBeanData(String url, Class<T> clazz) {

        //1. 去网络获取数据
        String content = HttpManager.getInstance().dataGet(url);
        //2. 判断当前的数据是否为空
        if (TextUtils.isEmpty(content)) {
            //如果是空
            //去缓存类中去取数据
            content = CacheManger.getInstance().getCacheData(url);

//            System.out.println("当前缓存数据:"+content.length());
        } else {
            //不为空
            //保存数据,刷新缓存数据
            CacheManger.getInstance().saveData(content,url);
        }

        if (TextUtils.isEmpty(content)) {
            return null;
        }

        //到这一步为止,我们已经得到数据了
        //解析一把
//        return (T) parseJson2(content);
        return parseJson(content,clazz);





        //走到这一步就可以去解析了
//        return null;
    }
    //获取json对象的方法
    public <T> T getBeanData2(String url, Class<T> clazz) {

        //1. 去网络获取数据
        String content = HttpManager.getInstance().dataGet(url);
        //2. 判断当前的数据是否为空
        if (TextUtils.isEmpty(content)) {
            //如果是空
            //去缓存类中去取数据
            content = CacheManger.getInstance().getCacheData(url);

//            System.out.println("当前缓存数据:"+content.length());
        } else {
            //不为空
            //保存数据,刷新缓存数据
            CacheManger.getInstance().saveData(content,url);
        }

        if (TextUtils.isEmpty(content)) {
            return null;
        }

        //到这一步为止,我们已经得到数据了
        //解析一把
        return (T) parseJson2(content);
//        return parseJson(content,clazz);
        //走到这一步就可以去解析了
//        return null;
    }

    //解析json数据
    private<T> T parseJson(String content, Class<T> clazz) {
     //   return  gson.fromJson(json, cls);
        return GsonUtil.parseJsonToBean(content,clazz);
//        return null;
    }

        //解析json数据
    private CommentBean parseJson2(String content) {
        Gson gson = new Gson();
        CommentBean commentBean = gson.fromJson(content, CommentBean.class);
        return commentBean;

    }



    //获取json对象的方法
    public <T> List<T> getListData(String url, Type type) {

        //1. 去网络获取数据
        String content = HttpManager.getInstance().dataGet(url);
        //2. 判断当前的数据是否为空
        if (TextUtils.isEmpty(content)) {
            //如果是空
            //去缓存类中去取数据
            content = CacheManger.getInstance().getCacheData(url);

//            System.out.println("当前缓存数据:"+content.length());
        } else {
            //不为空
            //保存数据,刷新缓存数据
            CacheManger.getInstance().saveData(content,url);
        }

        //到这一步为止,我们已经得到数据了
        //解析一把
        return parseJsonList(content,type);





        //走到这一步就可以去解析了
        //        return null;
    }

    private <T> List<T> parseJsonList(String content, Type type) {
        return GsonUtil.parseJsonToList(content,type);
    }


}
