package com.example.ordertest.dao;

import com.example.ordertest.dto.OrderDTO;
import com.example.ordertest.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> findAll();

    Order findByNumber(Integer number);

    int insertOrder(Order order);

    int updateOrder(Order order);

    int deleteOrder(Integer number);

    List<OrderDTO> findByProperty(OrderDTO orderDTO);
}
