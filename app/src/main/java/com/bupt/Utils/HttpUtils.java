package com.bupt.Utils;

import android.content.Context;
import android.location.Location;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by JohnVenn on 2015/9/23.
 *
 * 用Post方式跟服务器传递数据
 */
public class HttpUtils {

    public static String doPost(Context context,String url){
        String responseStr = "Fail";
        try {
            HttpPost httpRequest = new HttpPost(url);
            HttpParams params = new BasicHttpParams();
            ConnManagerParams.setTimeout(params, 1000); // 从连接池中获取连接的超时时间
            HttpConnectionParams.setConnectionTimeout(params, 3000);// 通过网络与服务器建立连接的超时时间
            HttpConnectionParams.setSoTimeout(params, 5000);// 读响应数据的超时时间
            httpRequest.setParams(params);
            // 下面开始跟服务器传递数据，使用BasicNameValuePair
            List<BasicNameValuePair> paramsList = new ArrayList<>();

            String latitude;
            String longitude;
            Location location = Data.getLocation(context);
            if(location != null) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            }
            else {
                latitude ="0.0";
                longitude = "0.0";
            }

            paramsList.add(new BasicNameValuePair("Wifi", Data.getBuptlevel(context)));
            paramsList.add(new BasicNameValuePair("GSM",Data.getGSMLevel(context)));
            paramsList.add(new BasicNameValuePair("Latitude", latitude));
            paramsList.add(new BasicNameValuePair("Longitude", longitude));
            paramsList.add(new BasicNameValuePair("Time",Data.getTime()));
            paramsList.add(new BasicNameValuePair("mac",Data.getMacAddress(context)));

            UrlEncodedFormEntity mUrlEncodeFormEntity = new UrlEncodedFormEntity(
                    paramsList, HTTP.UTF_8);
            httpRequest.setEntity(mUrlEncodeFormEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            final int ret = httpResponse.getStatusLine().getStatusCode();
            if (ret == HttpStatus.SC_OK) {
                // responseStr = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
                responseStr = "OK";
            } else {
                responseStr = String.valueOf(ret);
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return responseStr;
    }
}
