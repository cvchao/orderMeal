package com.mdd.admin.service;

import com.mdd.admin.vo.order.OrderDeskVo;
import com.mdd.admin.vo.order.OrderDishCateVo;
import com.mdd.admin.vo.order.OrderDishVo;

import java.util.List;

public interface IOrderService {

    List<OrderDeskVo> list();

    List<OrderDishCateVo> dishCate();

    List<OrderDishVo> dishList();
}
