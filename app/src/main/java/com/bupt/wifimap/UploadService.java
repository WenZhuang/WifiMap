package com.bupt.wifimap;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;
import android.widget.Toast;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.bupt.Utils.*;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadService extends IntentService {

    //   private static String serverUrl = "http://10.103.24.161:8080/DataReceiver/rest/data";
    private static String serverUrl = "http://192.168.199.160:8080/Data/rest/data";
    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String res = doPost(serverUrl);
        System.out.println(res);
    }

    /**
     * 用Post方式跟服务器传递数据
     *
     * @param url
     * @return
     */

    private String doPost(String url) {
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
            Location location = Data.getLocation(this);
            if(location != null) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            }
            else {
                latitude ="0.0";
                longitude = "0.0";
            }

            paramsList.add(new BasicNameValuePair("Wifi", Data.getBuptlevel(this)));
            paramsList.add(new BasicNameValuePair("GSM",Data.getGSMLevel(this)));
            paramsList.add(new BasicNameValuePair("Latitude", latitude));
            paramsList.add(new BasicNameValuePair("Longitude", longitude));
            paramsList.add(new BasicNameValuePair("Time",Data.getTime()));
            paramsList.add(new BasicNameValuePair("mac",Data.getMacAddress(this)));

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
