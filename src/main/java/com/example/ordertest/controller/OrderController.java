package com.example.ordertest.controller;

import com.example.ordertest.enums.OrderEnum;
import com.example.ordertest.kafka.ProductMsg;
import com.example.ordertest.model.Message;
import com.example.ordertest.model.Order;
import com.example.ordertest.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Api
@RestController
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    private ProductMsg productMsg;


    public OrderController(OrderService orderService, ProductMsg productMsg) {
        this.orderService = orderService;
        this.productMsg = productMsg;
    }

    //查询订单-查询所有
    @ApiOperation(value = "查询所有")
    @GetMapping
    public List<Order> findAll() {
        List<Order> orderList = orderService.findAll();
        if (orderList != null && orderList.size() > 0) {
            return orderList;
        }
        return null;
    }

    //查询订单-通过number编号查询
    @ApiOperation(value = "通过number编号查询", notes = "测试数据")
    @GetMapping("/{number}")
    public Object findByNumber(@PathVariable("number") Integer number) throws InterruptedException {

        if (number != null) {

            Order order = orderService.findByNumber(number);
            if (order != null) {
                if (order.getNumber() != -1) {
                    return order;
                } else {
                    return new Message(OrderEnum.MANYDATA.getMessage());
                }
            }
        }
        return new Message(OrderEnum.UNDATA.getMessage());
    }

    //新增-下单
    @ApiOperation("新增-下单")
    @PostMapping
    public Message insertOrder(@RequestBody Order order) {
        Message message;
        if (order != null) {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = dateformat.format(System.currentTimeMillis());
            order.setGmtCreate(dateStr);
            order.setGmtModified(order.getGmtCreate());
            log.info("对数据进行判断：" + order.toString());
            if (order.getNumber().equals(orderService.findByNumber(order.getNumber()).getNumber())) {
                message = new Message(OrderEnum.HAVEDATA.getMessage());
                return message;
            } else {
                //kafka发送消息
                productMsg.insertMessage(order);
                message = new Message(OrderEnum.INSERT.getMessage());
            }
        } else {
            message = new Message(OrderEnum.UNDATA.getMessage());
        }

        return message;
    }

    //修改订单
    @ApiOperation("修改订单")
    @PutMapping
    public Message updateOrder(@RequestBody Order order) throws InterruptedException {
        Message message;
        if (order != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = dateFormat.format(System.currentTimeMillis());
            order = orderService.findByNumber(order.getNumber());
            order.setGmtModified(dateStr);
            //kafka发送消息
            productMsg.updateOrder(order);
//            orderService.updateOrder(order);
            message = new Message(OrderEnum.UPDATE.getMessage());
        } else {
            message = new Message(OrderEnum.UNDATA.getMessage());
        }
        return message;
    }

    //删除
    @ApiOperation("根据number删除数据")
    @DeleteMapping("/{number}")
    public Message deleteOrder(@PathVariable(value = "number") Integer number) throws InterruptedException {
        Message message;
        if (number != null) {
            orderService.deleteOrder(number);
            message = new Message(OrderEnum.DELETE.getMessage());
        } else {
            message = new Message(OrderEnum.DELETE.getMessage());
        }
        return message;
    }
}

