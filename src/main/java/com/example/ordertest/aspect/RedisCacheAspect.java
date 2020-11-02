package com.example.ordertest.aspect;

import com.example.ordertest.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class RedisCacheAspect {

    //前缀
    private static final String CACHE_PREFIX = "order_";

    private RedisTemplate redisTemplate;

    public RedisCacheAspect(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(com.example.ordertest.annotation.RedisCache)")
    public void redisCachePointCut() {
    }

    @Pointcut("@annotation(com.example.ordertest.annotation.RedisAll)")
    public void redisCachAll() {
    }

    @Pointcut("@annotation(com.example.ordertest.annotation.RedisChangeCache)")
    public void redisCacheChangePointCut() {
    }

    @Around("redisCachePointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //类
        String className = joinPoint.getTarget().getClass().getName();
        //方法
        String methodName = joinPoint.getSignature().getName();
        //参数
        Object[] args = joinPoint.getArgs();

        log.info("redis环绕切面查询：" + "类：" + className + " 方法：" + methodName + " 参数：" + args);

        Object result = new Object();
        Order order = new Order();
        order.setNumber((Integer) args[0]);
        //查询--根据number查询
        if (methodName.contains("findByNumber")) {
            result = redisTemplate.opsForHash().get(CACHE_PREFIX, order.getNumber().toString());
            if (result != null) {
                return result;
            } else {
                result = joinPoint.proceed();

                Map<String, Order> map = new HashMap<>();
                map.put(order.getNumber().toString(), (Order) result);
                redisTemplate.opsForHash().putAll("order", map);
            }
        }

        //删除
        if (methodName.contains("delete")) {
            joinPoint.proceed();
            redisTemplate.opsForHash().delete("order", order.getNumber().toString());
        }

        return result;
    }

    @Around("redisCachAll()")
    public List<Order> doAllAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //类
        String className = joinPoint.getTarget().getClass().getName();
        //方法
        String methodName = joinPoint.getSignature().getName();
        //参数
        Object[] args = joinPoint.getArgs();

        log.info("redis环绕切面查询所有：" + "类：" + className + " 方法：" + methodName + " 参数：" + args);

        List<Order> result = new ArrayList<>();

        if (methodName.contains("findAll")) {
            List<Order> orderList = redisTemplate.opsForHash().values("order");
            if (orderList != null && orderList.size() > 0) {
                return result;
            } else {
                result = (List<Order>) joinPoint.proceed();
                Map<String, Order> map = new HashMap<>();
                //遍历
                for (Order order : result) {
                    map.put(order.getNumber().toString(), order);
                }
                redisTemplate.opsForHash().putAll("order", map);
            }
        }
        return result;
    }


    @After("redisCacheChangePointCut()")
    public void doChangeAfter(JoinPoint joinPoint) throws Throwable {
        //类
        String className = joinPoint.getTarget().getClass().getName();
        //方法
        String methodName = joinPoint.getSignature().getName();
        //参数
        Object[] args = joinPoint.getArgs();
        log.info("redis后置切面增删改：" + "类：" + className + " 方法：" + methodName + " 参数：" + args);

        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.convertValue(args[0], Order.class);

        if (order != null) {
            if (methodName.contains("insert")) {
                log.info("插入：" + order.toString());

                Map<String, Order> map = new HashMap<>();
                map.put(order.getNumber().toString(), order);
                redisTemplate.opsForHash().putAll("order", map);
            }

            if (methodName.contains("update")) {
                log.info("修改：" + order.toString());

                Map<String, Order> map = new HashMap<>();
                map.replace(order.getNumber().toString(), order);
                redisTemplate.opsForHash().delete("order", order.getNumber().toString());
                redisTemplate.opsForHash().putAll("order", map);
            }

        }

    }

}
