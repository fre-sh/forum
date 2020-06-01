package com.fresh.forum.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author guowenyu
 * @date 2020/5/27
 */
public class DateUtil {

    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        return date == null ? "" : df.format(date);
    }

}
