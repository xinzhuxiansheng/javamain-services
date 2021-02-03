package com.javamain.annotation.lang;

import com.alibaba.fastjson.JSONObject;
import com.javamain.jdk.util.RegxUtil;

public class StringDebug {

    public static void main(String[] args) {

        String str = "{\"AppID\":\"2\",\"AppVersion\":\"9.1\\t3.5\",\"AppChannel\":\"pvd\r\nebug\",\"CodeUserId\":\"0\r\n\",\"DeviceName\":\"OPPO R11s\",\"DeviceImei\":\"d0e1730c_5d9a_4b14_aa97_7080a79abf87\",\"DeviceOsVersion\":\"7.1.1\",\"DeviceOsType\":\"Android\",\"DeviceCpuType\":\"armeabi-v7a\",\"DeviceRomName\":\"OPPO\",\"NetType\":\"wifi\",\"Order\":\"0\",\"CodeLogType\":\"147000\",\"CodeLogSubType\":\"147001\",\"CodeLogMessage\":\"\",\"UserDefined\":\"{\\\"network\\\":\\\"NETWORK_TYPE_WIFI\\\",\\\"bundleVersion\\\":\\\"0.0.0\\\",\\\"bundleName\\\":\\\"Page2\\\",\\\"moduleName\\\":\\\"App\\\",\\\"bundleDownloadStartTime\\\":\\\"0\\\",\\\"bundleDownloadEndTime\\\":\\\"0\\\",\\\"bundleUnzipStartTime\\\":\\\"0\\\",\\\"bundleUnzipEndTime\\\":\\\"0\\\",\\\"bundleLoadStartTime\\\":\\\"0\\\",\\\"bundleLoadEndTime\\\":\\\"0\\\",\\\"renderStartTime\\\":\\\"0\\\",\\\"renderEndTime\\\":\\\"0\\\",\\\"currentMemory\\\":\\\"49\\\",\\\"bundleCommonLoadStartTime\\\":\\\"0\\\",\\\"bundleCommonLoadEndTime\\\":\\\"0\\\",\\\"bundleCommonPathStartTime\\\":\\\"1563154356959\\\",\\\"bundleCommonPathEndTime\\\":\\\"1563154357005\\\",\\\"bundlePathStartTime\\\":\\\"0\\\",\\\"bundlePathEndTime\\\":\\\"0\\\",\\\"bundleDownLoadInfo\\\":\\\"0\\\",\\\"bundleCommonDownloadStartTime\\\":\\\"0\\\",\\\"bundleCommonDownloadEndTime\\\":\\\"0\\\",\\\"bundleCommonUnzipStartTime\\\":\\\"0\\\",\\\"bundleCommonUnzipEndTime\\\":\\\"0\\\",\\\"subErrorType\\\":147001,\\\"errorInfo\\\":\\\"annotation.io.FileNotFoundException: https:\\\\\\/\\\\\\/optimus.xxxxhome.com.cn\\\\\\/download\\\\\\/plugins\\\\\\/static\\\\\\/9.13.5\\\\\\/Android_Page2.7z\\\\n\\\\tat com.android.okhttp.internal.huc.HttpURLConnectionImpl.getInputStream(HttpURLConnectionImpl.annotation:250)\\\\n\\\\tat com.android.okhttp.internal.huc.DelegatingHttpsURLConnection.getInputStream(DelegatingHttpsURLConnection.annotation:210)\\\\n\\\\tat com.android.okhttp.internal.huc.HttpsURLConnectionImpl.getInputStream(HttpsURLConnectionImpl.annotation)\\\\n\\\\tat com.xxxxhome.business.rnupdate.manager.HttpBundleDownloader.download(HttpBundleDownloader.annotation:236)\\\\n\\\\tat com.xxxxhome.business.rnupdate.manager.RNBundleDownloadManager.startCacheByVideoInfo(RNBundleDownloadManager.annotation:307)\\\\n\\\\tat com.xxxxhome.business.rnupdate.manager.RNBundleDownloadManager.access$100(RNBundleDownloadManager.annotation:22)\\\\n\\\\tat com.xxxxhome.business.rnupdate.manager.RNBundleDownloadManager$DownRunnable.run(RNBundleDownloadManager.annotation:270)\\\\n\\\\tat annotation.lang.Thread.run(Thread.annotation:761)\\\\n\\\",\\\"md5_errorInfo\\\":\\\"819d9428489528fb6cf50f618e7afa7f\\\",\\\"performaceWeight\\\":100}\",\"WriteLogDbTime\":1563154358395}";

        //将字符串 转json
//        JSONObject obj =  JSONObject.parseObject(str);
//
//        JSONObject ud = obj.getJSONObject("UserDefined");
//
//        String udstr = obj.getString("UserDefined");
//
//        if(udstr.indexOf("\t")>-1){
//            System.out.println("包含 \t ");
//        }else if(udstr.indexOf("\\t")>-1){
//            System.out.println("包含 \\t ");
//        }
//
//        System.out.println("udstr 长度: " + udstr.length() );
//
//       //udstr = udstr.replace("\t","");
//        udstr = udstr.replaceAll("\t","");
//
//        System.out.println("udstr 长度: " + udstr.length() );
//
//         String udstr2 = udstr.replaceAll("\\t","");
//        //udstr = udstr.replace("\\t","");
//        System.out.println("udstr 长度: " + udstr.length() );
        System.out.println("udstr 长度1: " + str.length() );
        System.out.println("udstr 长度1: " + str );
        //str = str.replaceAll("\\\\t","");
        str = RegxUtil.replaceKafkaSpecialCharacters(str);
        System.out.println("udstr 长度2: " + str.length() );
        System.out.println("udstr 长度2: " + str );
        JSONObject obj = JSONObject.parseObject(str);
        System.out.println(obj.toJSONString());
    }
}
