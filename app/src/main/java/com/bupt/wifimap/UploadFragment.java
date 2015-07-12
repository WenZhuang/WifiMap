package com.bupt.wifimap;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.
        params.ConnManagerParams;
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
import java.util.Calendar;
import java.util.List;
import android.content.Intent;

import com.bupt.Utils.Data;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //  private static String serverUrl = "http://10.103.24.161:8080/DataReceiver/rest/data";
    private static String serverUrl = "http://192.168.199.160:8080/Data/rest/data";

    // TODO: Rename and change types of parameters
    private int mParam1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadFragment newInstance(int param1) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_upload, container, false);

        Button manuUpload = (Button) rootView.findViewById(R.id.manuUpload);
        manuUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubmitAsyncTask().execute(serverUrl);
            }
        });

        final AlarmManager aManager = (AlarmManager) getActivity().getSystemService(Service.ALARM_SERVICE);
        final PendingIntent pi = PendingIntent.getService(
                getActivity(), 0, (new Intent(getActivity(),
                        UploadService.class)), 0);
        final Button autoUpload = (Button) rootView.findViewById(R.id.autoUpload);
        final Button stop =(Button) rootView.findViewById(R.id.stop);

        autoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aManager.setRepeating(AlarmManager.RTC_WAKEUP
                       , 0, 10000, pi);

                autoUpload.setEnabled(false);
                stop.setEnabled(true);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoUpload.setEnabled(true);
                stop.setEnabled(false);

                aManager.cancel(pi);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_PARAM1));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class SubmitAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = params[0];
            String reps = "";


            reps = doPost(url);

            return reps;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

            super.onPostExecute(result);
        }
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
            Location location = Data.getLocation(getActivity());
            if(location != null) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            }
            else {
                latitude ="0.0";
                longitude = "0.0";
            }

            paramsList.add(new BasicNameValuePair("Wifi", Data.getBuptlevel(getActivity())));
            paramsList.add(new BasicNameValuePair("GSM",Data.getGSMLevel(getActivity())));
            paramsList.add(new BasicNameValuePair("Latitude", latitude));
            paramsList.add(new BasicNameValuePair("Longitude", longitude));
            paramsList.add(new BasicNameValuePair("Time",Data.getTime()));
            paramsList.add(new BasicNameValuePair("mac",Data.getMacAddress(getActivity())));

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
