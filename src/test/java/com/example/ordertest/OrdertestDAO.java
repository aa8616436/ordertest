package com.example.ordertest;

import com.example.ordertest.dao.OrderMapper;
import com.example.ordertest.dto.OrderDTO;
import com.example.ordertest.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdertestDAO {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void selectTest() {
        List<Order> all = orderMapper.findAll();
        log.info("订单测试所有：" + all.toString());

        Order byNumber = orderMapper.findByNumber(1);
        log.info("根据数据查询：" + byNumber);
    }

    @Test
    public void insertTest() {
        Order order = new Order();
        order.setNumber(3);
        order.setName("3");
        order.setGmtModified("3");
        order.setGmtCreate("3");

        orderMapper.insertOrder(order);
        log.info("插入根据数据查询：" + orderMapper.findByNumber(order.getNumber()));
        orderMapper.deleteOrder(order.getNumber());
    }


    @Test
    public void updateTest() {
        Order order = new Order();
        order.setNumber(1);
        order.setName("3");
        order.setGmtModified("3");
        order.setGmtCreate("3");

        orderMapper.updateOrder(order);
        log.info("更新根据数据查询：" + orderMapper.findByNumber(order.getNumber()));

    }

    @Test
    public void selectByPropertyTest() {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setStartNumber(0);
        orderDTO.setAmount(2);
        orderDTO.setOrderBy("asc");

        List<OrderDTO> byProperty = orderMapper.findByProperty(orderDTO);
        log.info("属性查询：" + byProperty.toString());
    }
}
