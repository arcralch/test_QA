package com.htc.ngt.lambda.qa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateSystem {

    public String getCurrDateAsString(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        String currDate = formatDate.format(cal.getTime());
        return currDate;
    }

    public String getEndDateAsString(int numDay,String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, numDay);
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        String dateEnd = formatDate.format(cal.getTime());
        return dateEnd;
    }

}