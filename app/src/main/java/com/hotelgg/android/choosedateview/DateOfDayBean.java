package com.hotelgg.android.choosedateview;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by zangpeng on 2018/3/6.
 */

public class DateOfDayBean implements Serializable {
    private String dayOfMonth = "";
    private boolean canClick = false;
    private boolean isToday;
    private String holidayText = "";
    private Calendar calendar;

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = (Calendar) calendar.clone();
    }

    public String getHolidayText() {
        return holidayText;
    }

    public void setHolidayText(String holidayText) {
        this.holidayText = holidayText;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }
}
