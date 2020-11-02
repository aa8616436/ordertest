package com.example.ordertest.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    /**
     * id
     */
    @JSONField(name = "id", alternateNames = {"id"})
    private Integer id;


    /**
     * 编号
     */
    @JSONField(name = "number", alternateNames = "number")
    private Integer number;


    /**
     * 名称
     */
    @JSONField(name = "name", alternateNames = "name")
    private String name;


    /**
     * 下单日期
     *
     */
    @JSONField(name = "gmtCreate", alternateNames = "gmtCreate")
    private String gmtCreate;

    /**
     * 修改时间
     */
    @JSONField(name = "gmtModified", alternateNames = "gmtModified")
    private String gmtModified;

}
