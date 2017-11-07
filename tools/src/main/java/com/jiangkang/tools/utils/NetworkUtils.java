package com.jiangkang.tools.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.jiangkang.tools.King;
import com.jiangkang.tools.device.DeviceUtils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiangkang on 2017/9/22.
 */

public class NetworkUtils {

    private static final String TAG = DeviceUtils.class.getSimpleName();
    private static final String NETWORK_TYPE_WIFI = "wifi";
    private static final String NETWORK_TYPE_MOBILE = "mobile";
    private static final String NETWORK_TYPE_OTHER = "other";


    private static final String MAC_ADDRESS_DEFAULT = "02:00:00:00:00:00";

    private static WifiManager sWifiManager;

    private static ConnectivityManager sConnectivityManager;

    public static final String NETWORK_TYPE_OFFLINE = "offline";


    static {
        sWifiManager = (WifiManager) King.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        sConnectivityManager = (ConnectivityManager) King.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static String getNetWorkType() {
        NetworkInfo networkInfo = sConnectivityManager.getActiveNetworkInfo();
        NetworkInfo mobileInfo = sConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = sConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return NETWORK_TYPE_OFFLINE;
        } else if (wifiInfo != null && wifiInfo.isAvailable()) {
            return NETWORK_TYPE_WIFI;
        } else if (mobileInfo != null && mobileInfo.isAvailable()) {
            return NETWORK_TYPE_MOBILE;
        } else {
            return NETWORK_TYPE_OTHER;
        }
    }


    /*
    * 获取Wifi信号等级，分为0-4五个等级
    * */
    public static int getSignalLevel() {
        WifiInfo wifiInfo = sWifiManager.getConnectionInfo();
        return WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
    }


    public static String getMacAddress() {
        WifiInfo wifiInfo = sWifiManager.getConnectionInfo();
        String macAddress = wifiInfo.getMacAddress();
        if (!MAC_ADDRESS_DEFAULT.equals(macAddress)) {
            return macAddress;
        }

        macAddress = getMacAddressByNetworkInterface();
        if (!MAC_ADDRESS_DEFAULT.equals(macAddress)) {
            return macAddress;
        }
        return MAC_ADDRESS_DEFAULT;
    }


    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }


    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    private static String getMacAddressByWifiInfo() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifi = (WifiManager) King.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }


    public static boolean isNetAvailable() {
        return sConnectivityManager.getActiveNetworkInfo() != null;
    }


}
