package com.springdemo.greeting.repositories;

import java.util.Calendar;

/**
 * Created by Mirage on 27/1/17.
 */
public class TimeRepository {
    String getServerTime()
    {
        String timeStamp = Calendar.getInstance().getTime().toString();
        return timeStamp;
    }
}
