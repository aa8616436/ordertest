package com.example.ordertest.kafka;

import com.alibaba.fastjson.JSONObject;
import com.example.ordertest.model.Order;
import com.example.ordertest.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerMsg {

    private OrderService orderService;

    public ConsumerMsg(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = {"order_insert"})
    public void processInsertMessageOrder(String orderList) {
        Order order = JSONObject.parseObject(orderList, Order.class);
        int result = orderService.insertOrder(order);

/*        for (ConsumerRecord<?,?> record : records) {
            log.info(record.topic() + "--insert--" + record.value());

            Order order=JSONObject.parseObject((byte[]) record.value(),Order.class);
            int result = orderService.insertOrder(order);
        }*/
    }

    @KafkaListener(topics = {"order_update"})
    public void processUpdateMessageOrder(String orderList) {
        log.info(orderList.toString());

        Order order = JSONObject.parseObject(orderList, Order.class);
        int result = orderService.updateOrder(order);
    }

}
