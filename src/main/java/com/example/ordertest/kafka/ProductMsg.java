package com.example.ordertest.kafka;

import com.alibaba.fastjson.JSONObject;
import com.example.ordertest.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductMsg {

    private KafkaTemplate<String, String> kafkaTemplate;

    public ProductMsg(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void insertMessage(Order order) {
        log.info("-kafka消费者-插入-" + order.toString());
        String str = JSONObject.toJSONString(order);
        kafkaTemplate.send("order_insert", str);
    }

    public void updateOrder(Order order) {
        log.info("-kafka消费者-更新-" + order.toString());
        String str = JSONObject.toJSONString(order);
        kafkaTemplate.send("order_update", str);
    }
}
