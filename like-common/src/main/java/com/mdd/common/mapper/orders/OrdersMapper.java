package com.mdd.common.mapper.orders;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.orders.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrdersMapper extends IBaseMapper<Orders> {

    @Update("update la_orders set status=2 where desk_id=#{did} and status=1")
    void updateOrdersStatus(Integer did);
}
