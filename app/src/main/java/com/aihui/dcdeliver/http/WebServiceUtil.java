package com.aihui.dcdeliver.http;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


public class WebServiceUtil extends AsyncTask<Object, Object, String> {

    // WSDL文档中的命名空间
    //默认
    public static String targetNameSpace = "http://intf.service.hrp.iwell";
    public static String wsdlUrl         = "/services/pdaws";

/*
    private OnCallBack mOnCallBack;
	public interface OnCallBack{
		void  callBack(String result);
	}*/

    public WebServiceUtil() {//默认PDA
        this.targetNameSpace = "http://intf.service.hrp.iwell";
        this.wsdlUrl = "/services/pdaws";

    }

    public WebServiceUtil(Integer type) {
        if (type == 1) {//原pda接口
            this.targetNameSpace = "http://intf.service.hrp.iwell";
            this.wsdlUrl = "/services/pdaws";
        } else if (type == 2) {//微信接口
            this.targetNameSpace = "http://impl.service.hrp.iwell";
            this.wsdlUrl = "/axis2/services/weChatWebService";
        }
    }


    @Override
    protected String doInBackground(Object... params) {
        Log.d("WebServiceUtil", Thread.currentThread().getName());
        String WSDL = (String) params[0] + wsdlUrl;
        String METHOD = (String) params[1];

        // 根据命名空间和方法得到SoapObject对象
        SoapObject soapObject = new SoapObject(targetNameSpace,
                METHOD);
        if (params.length == 1) {
            soapObject.addProperty("par" + 0, params[2]);
        }


        if (params.length >= 3 && params[2] != null) {
            List<Object> list = (List<Object>) params[2];
            for (int i = 0; i < list.size(); i++) {
                soapObject.addProperty("par" + i, list.get(i));
            }
        }

        // 通过SOAP1.1协议得到envelop对象
        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        // 将soapObject对象设置为envelop对象，传出消息
        //		soapObject.addProperty("Content-Type", "text/xml; charset=utf-8");
        //		soapObject.addProperty("SOAPAction","http://WebXml.com.cn/" + "getRegionProvince");
        //		soapObject.addProperty("Content-Length", soapObject.toString().length());


        envelop.bodyOut = soapObject;
        envelop.dotNet = true;


        // 开始调用远程方法
        try {
            HttpTransportSE httpSE = new HttpTransportSE(WSDL, 10000);
            //			System.out.println("开始调用");
            httpSE.call(targetNameSpace + "/" + METHOD, envelop);
            // 得到远程方法返回的SOAP对象
            Object resultObj = envelop.getResponse();
            //Object resultObj = envelop.bodyIn;
            //{"recode":"0","results":"ok"}         //巡检
            //			System.out.println("调用结束");
            if (resultObj != null) {
                return resultObj.toString();
            } else {
                return "0$" + "返回空的内容";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() == null) {
                return "0$" + "空的错误";
            }
            StringBuffer msg = new StringBuffer(e.getMessage());
            for (StackTraceElement traceElement : e.getStackTrace()) {
                msg.append(traceElement.toString());
            }
            return "0$" + msg;

        } finally {

        }
    }

/*
    @Override
	protected void onPostExecute(String s) {
		mOnCallBack.callBack(s);
	}*/
}
