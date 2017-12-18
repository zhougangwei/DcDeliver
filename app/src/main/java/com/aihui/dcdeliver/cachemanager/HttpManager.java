package com.aihui.dcdeliver.cachemanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.aihui.dcdeliver.application.BaseApplication.SCOOKIE;

public class HttpManager
{

    private OkHttpClient mOkHttpClient = null;

    private HttpManager()
    {
        if (mOkHttpClient == null) {
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS).readTimeout(1, TimeUnit.SECONDS).build();

        }

    }
    
    private static HttpManager sHttpManager = new HttpManager();
    
    public static HttpManager getInstance()
    {
        return sHttpManager;
    }
    
    // 去网络获取数据
    public String dataGet(String url)
    {


        try
        {


            Request request = new Request.Builder()
                    .addHeader("Accept-Language", Locale.getDefault().toString())
                    .url(url).build();

            Response response = mOkHttpClient.newCall(request).execute();

            if (response.isSuccessful())
            {

             /*   InputStream inputStream = response.body().byteStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                StringBuffer stringBuffer = new StringBuffer();
                while ((len = inputStream.read(buffer)) != -1) {

                    stringBuffer.append(new String(new String(buffer, 0, len,"iso-8859-1").getBytes(),"UTF-8"));
                }


                return stringBuffer.toString();*/
                return response.body().string();
            }
            else
            {
                return null;
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
            
        }
    }

    public void login(String url) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("keep_login", "1");
            builder.add("username","153522217@qq.com");
            builder.add("pwd","123456789x");
            builder.add("Connection", "Keep-Alive");
            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            String content = response.body().string();

            HashMap<String, String> cookieMap = new HashMap<>();
            Headers headers = response.headers();
            List<String> cookies = headers.values("Set-Cookie");
            for (String cookie : cookies) {
                System.out.println("当前的cookie:"+cookie);
                String[] infos = cookie.split(";");
                for (int i = 0; i < infos.length; i++) {
                    String[] cook = infos[i].split("=");
                    if (cook.length>1) {
                        cookieMap.put(cook[0],cook[1]);
                    }
                }

            }


            //_user_behavior_   oscid

//           String cookieValue = "_user_behavior_="+cookieMap.get("_user_behavior_")+";oscid="+cookieMap.get("oscid")+";";
           String cookieValue = "oscid="+cookieMap.get("oscid")+";";
            SCOOKIE = cookieValue;

            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    //回复
    public void comment(String url) {
        try {

            System.out.println("获取的cookie:"+ SCOOKIE);
            FormBody.Builder builder = new FormBody.Builder();
            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .addHeader("Cookie",SCOOKIE)
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            String content = response.body().string();
            System.out.println("当前的内容:"+content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    
}
