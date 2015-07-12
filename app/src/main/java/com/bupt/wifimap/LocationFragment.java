package com.bupt.wifimap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // 定义LocationManager对象
    private LocationManager locManager;
    // 定义程序界面中的EditText组件
    private TextView show;

    private double latitute;
    private double longitude;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        show = (TextView) rootView.findViewById(R.id.location);
        // 创建LocationManager对象
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //打开GPS设置
        // GPSTips(locManager);
        // 从GPS获取最近的定位信息
        Location location = locManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);
        // 使用location根据EditText的显示
        updateView(location);
        // 设置每3秒获取一次GPS的定位信息
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                , 3000, 8, new LocationListener()  //①
        {
            @Override
            public void onLocationChanged(Location location)
            {
                // 当GPS定位信息发生改变时，更新位置
                updateView(location);
            }

            @Override
            public void onProviderDisabled(String provider)
            {

                updateView(null);
            }

            @Override
            public void onProviderEnabled(String provider)
            {
                // 当GPS LocationProvider可用时，更新位置
                updateView(locManager
                        .getLastKnownLocation(provider));
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras)
            {
            }
        });

        return rootView;
    }

    private void GPSTips(LocationManager alm) {
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getActivity(), "GPS已开启", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "请开启GPS！", Toast.LENGTH_SHORT).show();
        }
    }

    // 更新EditText中显示的内容
    public void updateView(Location newLocation)
    {
        if(newLocation!=null){
            show.setText("设备位置信息\n\n经度：");
            longitude = newLocation.getLongitude();
            show.append(String.valueOf(longitude));
            latitute = newLocation.getLatitude();
            show.append("\n纬度：");
            show.append(String.valueOf(latitute));
        }else{
            show.setText("无法获取地理位置信息");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
