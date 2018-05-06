package com.fwlog.james.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//根据日期输出周几
public class WeekUtil {
    public String dateToWeek(String dateTime){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar calendar = Calendar.getInstance();//获得一个日历
        Date date = null;
        try {
            date = f.parse(dateTime);
            calendar.setTime(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static void main(String[] args) {
        System.out.println(new WeekUtil().dateToWeek("2017-01-01"));
    }
}
