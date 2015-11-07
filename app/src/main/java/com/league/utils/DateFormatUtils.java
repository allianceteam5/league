package com.league.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pfy on 2015/11/8.
 */
public class DateFormatUtils {
    public static String TimeStamp2Date(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                timestamp * 1000));
    }

    public static String getAuctionPublishEndDateTime() {
        long current = System.currentTimeMillis() + 24 * 3600 * 1000;

        return new SimpleDateFormat("MM.dd/HH:mm").format(new Date(
                current)) + "(24h)";
    }

    public static String TimeStamp2DateChinese(long timestamp) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(
                timestamp * 1000));
    }

    public static long Date2TimeStamp(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String NowTimeStamp2Date() {
        return TimeStamp2Date(System.currentTimeMillis() / 1000);
    }

}
