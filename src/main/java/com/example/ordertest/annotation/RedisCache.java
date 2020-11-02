package com.example.ordertest.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisCache {

    /**
     * 默认失效时间为5分钟
     */
    int expire() default 300;

}
