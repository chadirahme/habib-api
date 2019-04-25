package com.habib.api.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chadirahme on 4/16/19.
 */
public abstract class AbstractRestController {

    String formatDate="yyyy-MM-dd";

    public String getCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public Date convertStringToDate(String date)
    {
        try {
            return new SimpleDateFormat(formatDate).parse(date);
        }
        catch (Exception ex){
         return null;
        }
    }
}
