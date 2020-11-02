package com.example.ordertest.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private Integer id;

    /**
     * 编号
     */
    private Integer number;


    /**
     * 名称
     */
    private String name;


    /**
     * 下单日期
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;

    /**
     * 排列顺序
     */
    private String orderBy;

    /**
     * 查询数据起点
     */
    private Integer startNumber;

    /**
     * 查询数据个数
     */
    private Integer amount;

}
