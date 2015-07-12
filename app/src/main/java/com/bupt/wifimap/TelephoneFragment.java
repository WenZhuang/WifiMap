package com.bupt.wifimap;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bupt.Utils.Data;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TelephoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TelephoneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TelephoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TelephoneFragment newInstance(String param1, String param2) {
        TelephoneFragment fragment = new TelephoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TelephoneFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_telephone, container, false);
        TextView telephoneInfo = (TextView) rootView.findViewById(R.id.telephoneInfo);
        telephoneInfo.setText( "手机品牌："+ Data.getBrand() + "\n手机型号：" + Data.getModel()
                + "\n系统版本：" + Data.getOSVersion()
                + "\nmac地址：" + Data.getMacAddress(getActivity())
                + "\n手机制式：" + Data.getPhoneType(getActivity())
                + "\n网络制式：" + Data.getNetworkType(getActivity()));

        return rootView;
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
