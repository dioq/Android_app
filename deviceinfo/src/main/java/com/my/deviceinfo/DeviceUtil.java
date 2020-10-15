package com.my.deviceinfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceUtil {

    private static final String TAG = "DeviceInfoModel";
    private static DeviceUtil instance;

    public static DeviceUtil getInstance() {
        if (instance == null) {
            instance = new DeviceUtil();
        }
        return instance;
    }

    /**
     * 获取品牌
     */
    public String getPhoneBrand() {
//        TelephonyManager manager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String mtype = android.os.Build.MODEL;
        String brand = Build.BRAND;//手机品牌
        return brand;
    }

    /**
     * 获取型号
     */
    public String getPhoneMODEL() {
        String model = Build.MODEL;//手机型号
        return model;
    }

    /**
     * 获取操作系统
     *
     * @return
     */
    public String getOS() {
        Log.w(TAG, "操作系统:" + "Android" + Build.VERSION.RELEASE);
        return "Android" + Build.VERSION.RELEASE;
    }

    /**
     * 获取手机分辨率
     *
     * @param context
     * @return
     */
    public String getResolution(Context context) {
        // 方法1 Android获得屏幕的宽和高
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        Log.w(TAG, "分辨率：" + screenWidth + "*" + screenHeight);
        return screenWidth + "*" + screenHeight;
    }

    /**
     * 获取运营商
     *
     * @param context
     * @return
     */
    public String getNetOperator(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String iNumeric = manager.getSimOperator();
        String netOperator = "";
        if (iNumeric.length() > 0) {
            if (iNumeric.equals("46000") || iNumeric.equals("46002")) {
                // 中国移动
                netOperator = "中国移动";
            } else if (iNumeric.equals("46003")) {
                // 中国电信
                netOperator = "中国电信";
            } else if (iNumeric.equals("46001")) {
                // 中国联通
                netOperator = "中国联通";
            } else {
                //未知
                netOperator = "未知";
            }
        }
        Log.w(TAG, "运营商：" + netOperator);
        return netOperator;
    }

    /**
     * 获取联网方式
     */
    public String getNetMode(Context context) {
        String strNetworkType = "未知";
//        TelephonyManager manager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        manager.getNetworkType();
        ConnectivityManager manager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int netMode = networkInfo.getType();
            if (netMode == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
                //wifi
            } else if (netMode == ConnectivityManager.TYPE_MOBILE) {
                int networkType = networkInfo.getSubtype();
                switch (networkType) {

                    //2g
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;

                    //3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;

                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;

                    default:
                        String _strSubTypeName = networkInfo.getSubtypeName();
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
            }
        }
        Log.w(TAG, "联网方式:" + strNetworkType);
        return strNetworkType;
    }

    //检查并获取权限
    private boolean checkReadPhoneStatePermission(Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 10);
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * @param context
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getIMEI(Context context) {
        if (!checkReadPhoneStatePermission(context)) {
            Log.w(TAG, "获取唯一设备号 IMEI : 无权限");
            return "";
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei1 = (String) method.invoke(manager, 0);
            String imei2 = (String) method.invoke(manager, 1);
            if (TextUtils.isEmpty(imei1)) {
                return imei2;
            }
            if (TextUtils.isEmpty(imei2)) {
                return imei1;
            }
            //因为手机卡插在不同位置，获取到的imei1和imei2值有可能会交换，所以取它们的最小值(最大值也行),保证拿到的imei都是同一个
            String imei = "";
            if (imei1.compareTo(imei2) <= 0) {
                imei = imei1;
            } else {
                imei = imei2;
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return manager.getDeviceId();
        }
    }

    // 获取MEID
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getMEID(Context context) {
        if (!checkReadPhoneStatePermission(context)) {
            Log.w(TAG, "获取唯一设备号-getMEID: 无权限");
            return "";
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        String meid = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String meid1 = tm.getMeid(0);
            String meid2 = tm.getMeid(1);
            if (TextUtils.isEmpty(meid1)) {
                return meid2;
            }
            if (TextUtils.isEmpty(meid2)) {
                return meid1;
            }
            if (meid1.compareTo(meid2) <= 0) {
                meid = meid1;
            } else {
                meid = meid2;
            }
        } else {
            meid = tm.getDeviceId();
        }
        return meid;
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取wifi当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
    }

    //GPRS连接下的ip
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("IpAddress", ex.toString());
        }
        return null;
    }

    //获取蓝牙地址
    public static String getBtAddressByReflection() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Field field = null;
        try {
            field = BluetoothAdapter.class.getDeclaredField("mService");
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            Object obj = method.invoke(bluetoothManagerService);
            if (obj != null) {
                return obj.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取序列号
    public String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

}
