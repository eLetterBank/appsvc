package com.springdemo.app;

import com.springdemo.greeting.contracts.GreetingQueryController;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingQueryController.class)
@AutoConfigureMockMvc
public class GreetingQueryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void serviceHealthTestReturnsOk() throws Exception {
        String expectedData = "Okay!";
        mvc.perform(MockMvcRequestBuilders.get("/api/qry/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.containsString(expectedData)));
    }

    @Test
    public void serviceGreetingTestReturnsSuccess() throws Exception {
        String expectedData = "TestMe";
        String greetingQry = "{\"name\":\"TestMe\",\"address\":{\"street\":\"Hamlin St.\"}}";
        mvc.perform(MockMvcRequestBuilders.get("/api/qry/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(Matchers.containsString(expectedData)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnCode").value(0))
                .andDo(MockMvcResultHandlers.print());
    }
}