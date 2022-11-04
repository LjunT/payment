package cn.qmso.wxPay.base;

import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import com.xkzhangsan.time.formatter.DateTimeFormatterUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateTimeZoneUtil implements Serializable {
    /**
     * 时间转 TimeZone
     *
     * @param time
     * @return
     * @throws Exception
     */
    public static String dateToTimeZone(long time) throws Exception {
        return dateToTimeZone(new Date(time));
    }

    /**
     * 时间转 TimeZone
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateToTimeZone(Date date) throws Exception {
        String time;
        if (date == null) {
            throw new Exception("date is not null");
        }
        ZonedDateTime zonedDateTime = DateTimeConverterUtil.toZonedDateTime(date);
        time = DateTimeFormatterUtil.format(zonedDateTime, DateTimeFormatterUtil.YYYY_MM_DD_T_HH_MM_SS_XXX_FMT);
        return time;
    }

    /**
     * TimeZone 时间转标准时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String timeZoneDateToStr(String str) throws Exception {
        String time;
        if (StringUtils.isBlank(str)) {
            throw new Exception("str is not null");
        }
        ZonedDateTime zonedDateTime = DateTimeFormatterUtil.parseToZonedDateTime(str, DateTimeFormatterUtil.YYYY_MM_DD_T_HH_MM_SS_XXX_FMT);
        if (zonedDateTime == null) {
            throw new Exception("str to zonedDateTime fail");
        }
        time = zonedDateTime.format(DateTimeFormatterUtil.YYYY_MM_DD_HH_MM_SS_FMT);
        return time;
    }
}