package com.fresh.forum.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author guowenyu
 * @date 2020/5/27
 */
public class DateUtil {

    public static final DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 到秒
     * @param date
     * @return
     */
    public static String format2s(Date date) {
        return date == null ? "" : dfs.format(date);
    }

    /**
     * 到天
     * @param date
     * @return
     */
    public static String format2d(Date date) {
        return date == null ? "" : dfd.format(date);
    }

}
