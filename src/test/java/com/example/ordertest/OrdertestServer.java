package com.example.ordertest;

import com.example.ordertest.model.Order;
import com.example.ordertest.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdertestServer {

    @Autowired
    OrderService orderSerivce;
    Order order = new Order();

    @Before
    public void init() {
        order.setNumber(1);
        order.setName("4444");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(System.currentTimeMillis());
        order.setGmtCreate(dateStr);
        order.setGmtModified(order.getGmtCreate());
    }

    @Test
    public void findByNumber() throws InterruptedException {
        //查询
//        Order order = orderSerivce.findByNumber(1);
        log.info("查询"+order.toString());
    }

    @Test
    public void insertTest() {
        int result = orderSerivce.insertOrder(order);
        log.info("新增"+result);
    }

    @Test
    public void updateTest() {
        int result = orderSerivce.updateOrder(order);
        log.info("修改"+result);
    }

    @Test
    public void deletetTest() throws InterruptedException {
        int result = orderSerivce.deleteOrder(1);
        log.info("删除"+result);
    }
}
