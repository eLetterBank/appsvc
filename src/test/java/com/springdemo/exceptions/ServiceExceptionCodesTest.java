package com.springdemo.exceptions;

import org.junit.Test;

public class ServiceExceptionCodesTest {

    @Test(expected = ServiceException.class)
    public void testMissingQueryParameterException() throws ServiceException {
        throw new ServiceException(ReturnCodes.MISSING_QUERY_PARAMETER);
    }
}