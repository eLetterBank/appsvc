//package com.springdemo.greeting.contracts;
package com.springdemo.app;

import com.springdemo.greeting.contracts.GreetingCommandController;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingCommandControllerTest.class)
@AutoConfigureMockMvc
public class GreetingCommandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHomeTestReturnsOk() throws Exception {
        String expectedData = "Hello World!";
        mvc.perform(MockMvcRequestBuilders.get("/api/cmd/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.containsString(expectedData)));
    }
}