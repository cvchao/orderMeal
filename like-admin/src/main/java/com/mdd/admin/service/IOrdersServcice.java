package com.mdd.admin.service;

import com.mdd.admin.validate.OrdersSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.orders.OrdersListVo;
import com.mdd.common.core.PageResult;

public interface IOrdersServcice {
    PageResult<OrdersListVo> list(PageValidate pageValidate, OrdersSearchValidate searchValidate);
}
