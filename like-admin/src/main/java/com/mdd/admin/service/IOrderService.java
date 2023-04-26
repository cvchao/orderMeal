package com.mdd.admin.service;

import com.mdd.admin.validate.order.DishAddValidate;
import com.mdd.admin.validate.order.OrderSubmitValidate;
import com.mdd.admin.validate.order.OrdersCreateValidate;
import com.mdd.admin.vo.order.OrderDeskVo;
import com.mdd.admin.vo.order.OrderDishCateVo;
import com.mdd.admin.vo.order.OrderDishVo;

import java.util.List;

public interface IOrderService {

    List<OrderDeskVo> list();

    List<OrderDishCateVo> dishCate();

    List<OrderDishVo> dishList();

    /**
     * 创建订单
     * @param ordersCreateValidate
     */
    Integer ordersCreate(OrdersCreateValidate ordersCreateValidate);
    /**
     * 菜品加购
     */
    void dishAdd(DishAddValidate dishAddValidate);

    void submit(OrderSubmitValidate orderSubmitValidate);

}
