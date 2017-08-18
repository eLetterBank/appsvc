package com.springdemo.app;

import com.springdemo.exceptions.ReturnCodes;
import com.springdemo.greeting.contracts.GreetingController;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
@AutoConfigureMockMvc
public class GreetingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void serviceHealthTestReturnsOk() throws Exception {
        String expectedData = "Okay!";
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/")
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-nonce", "v1")
                .header("x-vsolv-signature", "v2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.containsString(expectedData)));
    }

    @Test
    public void serviceGreetingTestReturnsSuccess() throws Exception {
        String expectedData = "TestMe";
        String greetingQry = "{\"name\":\"TestMe\",\"address\":{\"street\":\"Hamlin St.\"}}";
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-nonce", "v1")
                .header("x-vsolv-signature", "v2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(Matchers.containsString(expectedData)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnCode").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void serviceGreetingTestReturnsInvalidRequestForNoQryInput() throws Exception {
        String greetingQry = "{}";
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-nonce", "v1")
                .header("x-vsolv-signature", "v2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnCode").value(ReturnCodes.INVALID_REQUEST.getId()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void serviceGreetingTestReturnsInvalidRequestForPartialQryInput() throws Exception {
        String expectedData = "TestMe";
        String greetingQry = "{\"address\":{\"street\":\"Hamlin St.\"}}";
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-nonce", "v1")
                .header("x-vsolv-signature", "v2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnCode").value(ReturnCodes.INVALID_REQUEST.getId()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void serviceGreetingTestReturnsBadRequest() throws Exception {
        String expectedData = "TestMe";
        String greetingQry = "{\"name\":\"TestMe\",\"address\":{\"street\":\"Hamlin St.\"}}";

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-nonce", "v1"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-signature", "v2"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-nonce", "v11")
                .header("x-vsolv-signature", "v2"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/greeting")
                .param("qry", greetingQry)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-vsolv-nonce", "v1")
                .header("x-vsolv-signature", "v22"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
    }
}