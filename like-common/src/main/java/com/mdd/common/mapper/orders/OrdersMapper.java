package com.mdd.common.mapper.orders;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.orders.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends IBaseMapper<Orders> {
}
