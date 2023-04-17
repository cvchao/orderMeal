package com.mdd.admin.validate.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer type;//订单类型

    private Integer status;//订单状态

    private Long checkoutTimeStart;//结账时间开始

    private Long checkoutTimeEnd;//结账时间结束

    private Long createTimeStart;//创建时间开始

    private Long createTimeEnd;//创建时间结束

}
