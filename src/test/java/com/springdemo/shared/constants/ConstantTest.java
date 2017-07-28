package com.springdemo.shared.constants;

import com.springdemo.exceptions.ServiceException;
import com.springdemo.greeting.repositories.TimeRepository;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ConstantTest {
    @Test(expected = IllegalStateException.class)
    public void createInstanceOfConstantShouldThrowException()throws IllegalStateException {
        Constant cons = new Constant();
    }
}