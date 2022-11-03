/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: DateTimeZoneUtil
 * Author:   杨朝湖
 * Date:     2021/3/5 10:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.base;

import cn.hutool.core.util.StrUtil;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import com.xkzhangsan.time.formatter.DateTimeFormatterUtil;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/5
 * @since 1.0.0
 */
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
        if (StrUtil.isBlank(str)) {
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