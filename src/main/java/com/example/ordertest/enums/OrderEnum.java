package com.example.ordertest.enums;

public enum OrderEnum {
    INSERT("添加成功"),
    UPDATE("更新成功"),
    DELETE("删除成功"),
    UNDATA("数据不存在"),
    MANYDATA("查询数据不唯一"),
    HAVEDATA("数据库中已存在数据");
    private String message;

    OrderEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
