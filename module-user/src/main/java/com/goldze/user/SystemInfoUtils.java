package com.goldze.user;
/**
 * <b>类名称：</b> SystemInfoUtils<br/>
 * <b>类描述：</b>系统信息工具类<br/>
 * <b>创建人：</b> 林肯 <br/>
 * <b>修改人：</b> 编辑人 <br/>
 * <b>修改时间：</b> 2015年07月29日 下午2:18 <br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;

import java.util.Locale;
import java.util.UUID;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public final class SystemInfoUtils {
    /**
     * 手机系统选择的语言
     */
    public static String sLocale = null;

    /**
     * 私有的构造方法.
     */
    private SystemInfoUtils() {

    }

    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public static String getSysInternationalization(Context context) {
        if (!TextUtils.isEmpty(sLocale)) {
            return sLocale;
        }
        Resources resources = context.getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        return config.locale.toString();
    }

    /**
     * 获取渠道，用于打包，by weatherfish
     *
     * @param context
     * @param metaName
     * @return
     */
    public static String getAppSource(Context context, String metaName) {
        String result = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData == null)
                return "Android";
            result = appInfo.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getImei(final Context context) {
        String szImei = "";
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                szImei = TelephonyMgr.getDeviceId();
            }
            return szImei;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return szImei;
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String getNetType(final Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
//		networkInfo.getDetailedState();//获取详细状态。
//		networkInfo.getExtraInfo();//获取附加信息。
//		networkInfo.getReason();//获取连接失败的原因。
//		networkInfo.getType();//获取网络类型(一般为移动或Wi-Fi)。
//		networkInfo.getTypeName();//获取网络类型名称(一般取值“WIFI”或“MOBILE”)。
//		networkInfo.isAvailable();//判断该网络是否可用。
//		networkInfo.isConnected();//判断是否已经连接。
//		networkInfo.isConnectedOrConnecting();//：判断是否已经连接或正在连接。
//		networkInfo.isFailover();//：判断是否连接失败。
//		networkInfo.isRoaming();//：判断是否漫游
        return networkInfo == null ? "" : networkInfo.getTypeName();
    }


    /**
     * 获取包名 by weatherfish
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取App版本号.
     *
     * @param context 上下文信息
     * @return App版本号
     */
    public static int getAppVersionCode(final Context context) {
        int iAppVersionCode = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            iAppVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return iAppVersionCode;
    }

    /**
     * 获取App版本名.
     *
     * @param context 上下文信息
     * @return App版本名
     */
    public static String getAppVersionName(final Context context) {
        String strAppVersionName = "unknow";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            strAppVersionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return strAppVersionName;
    }


    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static String getPhoneSize(final Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();

        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）

        return screenWidth + "*" + screenHeight;
    }

    /**
     * 唯一识别码ID
     * @param context
     * @return
     */
    public static String getDeviceId(final Context context){


        String tmDevice = "";
        String tmSerial = "";
        String deviceid = "";
        try{
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= 23){
                int permissionCheck = ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_PHONE_STATE);
                if (permissionCheck!=-1){
                   // tmDevice = "" + tm.getDeviceId();
                    tmSerial = "" + tm.getSimSerialNumber();
                }
            }else {
                tmDevice = "" + tm.getDeviceId();
                tmSerial = "" + tm.getSimSerialNumber();
            }
            String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            deviceid = deviceUuid.toString();
        }catch (Exception ex){
        }

        return deviceid;
    }

    /**
     * 获取设备制造商名称.
     *
     * @return 设备制造商名称
     */
    public static String getManufacturerName() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备名称.
     *
     * @return 设备名称
     */
    public static String getModelName() {
        return Build.MODEL;
    }

    /**
     * 获取产品名称.
     *
     * @return 产品名称
     */
    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * 获取品牌名称.
     *
     * @return 品牌名称
     */
    public static String getBrandName() {
        return Build.BRAND;
    }

    /**
     * 获取操作系统版本号.
     *
     * @return 操作系统版本号
     */
    public static int getOSVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取操作系统版本名.
     *
     * @return 操作系统版本名
     */
    public static String getOSVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取操作系统版本显示名.
     *
     * @return 操作系统版本显示名
     */
    public static String getOSVersionDisplayName() {
        return Build.DISPLAY;
    }







    /**
     * 获取主机地址.
     *
     * @return 主机地址
     */
    public static String getHost() {
        return Build.HOST;
    }


    public final class CommonConsts {

        /**
         * 设备分类
         */
        public static final String SourceType = "Android";

        /**
         * 空格字符�?
         */
        public static final String SPACE = " ";

        /**
         * 逗号.
         */
        public static final String COMMA = ",";

        /**
         * 句点.
         */
        public static final String PERIOD = ".";

        /**
         * 左引�?
         */
        public static final String LEFT_QUOTES = "'";

        /**
         * 右引�?
         */
        public static final String RIGHT_QUOTES = "'";

        /**
         * 左圆括号.
         */
        public static final String LEFT_PARENTHESIS = "(";

        /**
         * 右圆括号.
         */
        public static final String RIGHT_PARENTHESIS = ")";

        /**
         * 左方括号.
         */
        public static final String LEFT_SQUARE_BRACKET = "[";

        /**
         * 右方括号.
         */
        public static final String RIGHT_SQUARE_BRACKET = "]";

        /**
         * 换行�?
         */
        public static final String LINE_BREAK = "\r\n";

        /**
         * 换行�?
         */
        public static final String LINE_BREAK_SHORT = "\n";


        /**
         * 问号.
         */
        public static final String QUESTION_MARK = "?";

        /**
         * 符号&.
         */
        public static final String AMPERSAND = "&";

        /**
         * 等于�?
         */
        public static final String EQUAL = "=";

        /**
         * 分号.
         */
        public static final String SEMICOLON = ";";
        /**
         * App渠道对应的meta名字
         */
        public static final String APP_SOURCE = "HET_CHANNEL";

        /**
         * 私有的构造方�?
         */
        private CommonConsts() {
        }
    }

    /**
     * 判断系统语言是否为英文
     * @return
     */
    public static boolean isZh(){
        Locale locale = Locale.getDefault();

//Locale.getDefault() 和 LocaleList.getAdjustedDefault().get(0) 同等效果，还不需要考虑版本问题，推荐直接使用
        String language = locale.getLanguage() + "-" + locale.getCountry();
        if (language.contains("zh")){
            return  true;
        }else {
            return false;
        }
    }

}
