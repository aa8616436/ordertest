package com.example.ordertest;


import com.example.ordertest.controller.OrderController;
import com.example.ordertest.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdertestApplicationTests {

    @Autowired
    OrderController orderController;


    @Autowired
    private WebApplicationContext wac;
    MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void findTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/orders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void findAllTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/orders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void insertTest() throws Exception {
        ObjectMapper om = new ObjectMapper();
        Order order = new Order();
        order.setNumber(1);
        order.setName("aaa");
        String str = om.writeValueAsString(order);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(str))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void updateTest() throws Exception {
        ObjectMapper om = new ObjectMapper();
        Order order = new Order();
        order.setNumber(2);
        order.setName("bbb");
        String str = om.writeValueAsString(order);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(str))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deletTest() throws Exception {
        ObjectMapper om = new ObjectMapper();
        Order order = new Order();
        order.setNumber(2);
        order.setName("bbb");
        String str = om.writeValueAsString(order);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(str))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

}
