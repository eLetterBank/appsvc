package com.springdemo.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationPropertiesTest {
    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    public void testLoadingOfProperties() {
        System.out.println("sample = " + applicationProperties.toString());

        assertThat(applicationProperties.getHttpHeader().getvSolvNonce(), equalTo("v1"));
        assertThat(applicationProperties.getHttpHeader().getvSolvSignature(), equalTo("v2"));
    }
}