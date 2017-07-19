package com.springdemo.greeting.contracts.shared;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressTest {

    private Address add;
    @BeforeClass
    public static void initialize() {
        System.out.println("This is printed only once");
    }
    @AfterClass
    public static void destroy() {
        System.out.println("The last thing printed");
    }
    @Before
    public void setUpAddressTest() {
        add = new Address();
    }

    @Test
    public void streetTest() throws Exception {
        String inStreet = "Street";
        add.setStreet(inStreet);
        String outStreet = add.getStreet();
        assertEquals(inStreet, outStreet);
    }
}