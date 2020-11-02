package com.example.ordertest.service;

import com.example.ordertest.annotation.RedisAll;
import com.example.ordertest.annotation.RedisCache;
import com.example.ordertest.annotation.RedisChangeCache;
import com.example.ordertest.dao.OrderMapper;
import com.example.ordertest.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {

    private OrderMapper orderMapper;


    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * 获取order：先从缓存中获取，没有则取数据表中数据，再将数据写入缓存
     *
     * @param number
     * @return
     */
    @RedisCache
    public Order findByNumber(Integer number) {
        Order order = orderMapper.findByNumber(number);
        return order;
    }

    /**
     * 获取list
     *
     * @return
     */
    @RedisAll
    public List<Order> findAll() {
        return orderMapper.findAll();
    }


    /**
     * 插入order:将新插入的数据进行缓存
     *
     * @param order
     */
    @RedisChangeCache
    public int insertOrder(Order order) {

        int result = orderMapper.insertOrder(order);
        return result;
    }

    /**
     * 更新order：先更新数据表，成功之后，删除原来的缓存，再更新缓存
     */
    @RedisChangeCache
    public int updateOrder(Order order) {
        int result = orderMapper.updateOrder(order);
        return result;
    }


    /**
     * 删除order：删除表中数据，然后删除缓存
     *
     * @param number
     * @return
     */
    @RedisCache
    public int deleteOrder(Integer number) throws InterruptedException {
        int result = orderMapper.deleteOrder(number);
        return result;
    }

}
