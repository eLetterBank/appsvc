package com.springdemo.greeting.repositories;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TimeRepositoryTest {
    @Test
    public void isServerTimeReturnsCurrentSeerverTime() {
        TimeRepository timeRepo = new TimeRepository();
        // Get the current date time
        LocalDateTime time = LocalDateTime.now();

        // Make sure that it is before or equals
        Assert.assertTrue(time.isBefore(timeRepo.getServerTime()) || time.isEqual(timeRepo.getServerTime()));
    }
}