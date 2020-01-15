package com.tangwh.sinatv;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AliPlayAndPushUtils {


    /**
     * 推流域名 阿里云配置的推流域名
     */
    private static final String pushDomain = "live.bajie188.com";
    /**
     * 拉流域名 阿里云配置的拉流域名
     */
    private static final String pullDomain = null;
    /**
     * // 自定义的StreaNmae
     */
    private static final String StreaName = "StreamName";
    /**
     * appName
     */
    private static final String appName = "AppName";
    /**
     * 鉴权key: 阿里云创建了推流域名和播流域名过后，他给生成的，每个域名一个，推流用推流的key，播流用播流的key，我这里只有一个key，因为我自己定义的一个，然后去阿里云里面把他们两个的权鉴备用key都设置成我设置的了
     */
    private static final String key = "xixian123";


    public static String dateToStamp() throws ParseException {

        Long time = System.currentTimeMillis();

        time += 30 * 100 * 60;

        String res;
        // 获取当前系统时间为
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));


        long ts = date.getTime(); // 得出的是毫秒 阿里云不需要毫秒,除以1000
        ts = ts / 1000;
        res = String.valueOf(ts);
        return res;

    }

    /**
     * @param time 十位数的时间戳
     * @return 推流的地址
     */
    public static String CreatePushUrl(String streamName, long time) {
        String strpush = "/" + appName + "/" + streamName + "-" + time + "-0-0-" + key;
        String pushUrl = "rtmp://" + pushDomain + "/" + appName + "/" + streamName + "?auth_key=" + time + "-0-0-" + MD5Utils.getMD5(strpush);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>推流:   " + pushUrl);
        return pushUrl;

    }

    /**
     * @param time 十位数的时间戳
     *             //     * @param rand       这是用来标识的 否则同一个时间戳 生成的地址总是相同的  随机数，建议使用UUID（不能包含中划线“-”，例如： 477b3bbc253f467b8def6711128c7bec 格式）
     * @return 播放流的地址 默认是flv  也可以更改此代码
     */
    public static String GetPlayUrl(String streamName, long time) {
        String strviewrtmp1 = null;
        String strviewflv1 = null;
        String strviewm3u81 = null;

        String rtmpurl1 = null;
        String flvurl1 = null;
        String m3u8url1 = null;

        strviewrtmp1 = "/" + appName + "/" + streamName + "-" + time + "-0-0-" + key;
        strviewflv1 = "/" + appName + "/" + streamName + ".flv-" + time + "-0-0-" + key;
        strviewm3u81 = "/" + appName + "/" + streamName + ".m3u8-" + time + "-0-0-" + key;
        rtmpurl1 = "rtmp://" + pullDomain + "/" + appName + "/" + streamName + "?auth_key=" + time + "-0-0-" + MD5Utils.getMD5(strviewrtmp1);
        flvurl1 = "http://" + pullDomain + "/" + appName + "/" + streamName + ".flv?auth_key=" + time + "-0-0-" + MD5Utils.getMD5(strviewflv1);
        m3u8url1 = "http://" + pullDomain + "/" + appName + "/" + streamName + ".m3u8?auth_key=" + time + "-0-0-" + MD5Utils.getMD5(strviewm3u81);

        Map<String, String> resultMap = new HashMap<>(5);
        resultMap.put("rtm", rtmpurl1);
        resultMap.put("flv", flvurl1);
        resultMap.put("m3u8", m3u8url1);
//        System.out.println(">>>>>>>>>>>>>>>>>>拉流:  " + rtmpurl1);
//        System.out.println(">>>>>>>>>>>>>>>>>>拉流:    " + flvurl1);
//        System.out.println(">>>>>>>>>>>>>>>>>>拉流:    " + m3u8url1);
        //  return //JSONObject.toJSONString(resultMap);
         return null;
    }
}
