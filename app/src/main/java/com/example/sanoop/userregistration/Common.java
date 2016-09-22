package com.example.sanoop.userregistration;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by sanoop on 9/22/2016.
 */
public class Common {

    public static final String MACID= "MACID";
    public static final String DEVICEID= "DEVICEID";
    public static final String IMEI= "IMEI";

    public static String getDeviceId(Context context, String deviceType){
        String deviceId = null;
        switch (deviceType){
            case MACID:
                String macAddr = getMacAddress(context);
                if (macAddr == null) {
                    Toast.makeText(context, "Device doesn't have mac address or wi-fi is disabled", Toast.LENGTH_LONG).show();
                }else{
                    deviceId = macAddr;
                }
                break;
            case DEVICEID:
                deviceId = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                break;
            case IMEI:
                String imeiId = getIMEI(context);
                if (imeiId == null) {
                    Toast.makeText(context, "Could not get IMEI ID", Toast.LENGTH_LONG).show();
                }else{
                    deviceId = imeiId;
                }
                break;
            default:
                break;
        }
        return deviceId;
    }

    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if(wifi == null) return "Failed: WiFiManager is null";

            WifiInfo info = wifi.getConnectionInfo();
            if(info == null) return "Failed: WifiInfo is null";

            return info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Nothing";
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
