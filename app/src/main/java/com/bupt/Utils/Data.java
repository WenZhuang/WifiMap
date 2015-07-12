package com.bupt.Utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;

import java.security.PrivateKey;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by JohnVenn on 2015/5/13.
 */
public class Data {

    public static String getMacAddress(Context context){
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }


    public static String getBuptlevel(Context context){
        WifiManager wifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> list = wifiManager.getScanResults();

        int buptLevel = -99;
        for(int i=0 ; i < list.size() ; i++){
            if((list.get(i).SSID).equals("BUPT-2") && (list.get(i).level) > buptLevel ){
                buptLevel = list.get(i).level;
            }
            else {
                continue;
            }
        }
        return String.valueOf(buptLevel);
    }

    public static String getGSMLevel(Context context){

        int signalStrength = -99;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> list = telephonyManager.getAllCellInfo();

        for(int i = 0 ; i < list.size(); i++) {
            if (list.get(i) instanceof CellInfoGsm) {
                CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(i);
                CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
                int s = cellSignalStrengthGsm.getDbm();
                if( s > signalStrength) {
                    signalStrength = s;
                }
            }
        }

        return String.valueOf(signalStrength);
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp nowdate = new Timestamp(System.currentTimeMillis());
        String datestr = sdf.format(nowdate);
        return datestr;
    }

    public static Location getLocation(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        return location;
    }

    public static String getBrand(){
        return Build.BRAND;
    }

    public static String getModel(){
        return Build.MODEL;
    }

    public static String getOSVersion(){
        return "Android"+Build.VERSION.RELEASE;
    }

    public static String getPhoneType(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephonyManager.getPhoneType();
        String phoneType = null;
        switch(type){
            case 0:
                phoneType = "NONE";
                break;
            case 1:
                phoneType = "GSM";
                break;
            case 2:
                phoneType = "CDMA";
                break;
            case 3:
                phoneType = "SIP";
                break;
            default:
                phoneType ="Unknown";
        }

        return  phoneType;
    }


    public static String getNetworkType(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephonyManager.getNetworkType();
        String networkType = null;
        switch(type){
            case 0:
                networkType = "Unknown";
                break;
            case 1:
                networkType = "GPRS";
                break;
            case 2:
                networkType = "EDGE";
                break;
            case 3:
                networkType = "UMTS";
                break;
            case 4:
                networkType = "CDMA";
                break;
            case 5:
                networkType = "EVDO0";
                break;
            case 6:
                networkType = "EVDOA";
                break;
            case 7:
                networkType = "1xRTT";
                break;
            case 8:
                networkType = "HSDPA";
                break;
            case 9:
                networkType = "HSUPA";
                break;
            case 10:
                networkType = "HSPA";
                break;
            case 11:
                networkType = "iDen";
                break;
            case 12:
                networkType = "EVDOB";
                break;
            case 13:
                networkType = "LTE";
                break;
            case 14:
                networkType = "eHRPD";
                break;
            case 15:
                networkType = "HSPA+";
                break;

            default:
                networkType ="Unknown";
        }

        return networkType;
    }


//  Returns the unique device ID, for example, the IMEI for GSM and the MEID or ESN for CDMA phones.
    public static String getDeviceId(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }
}
