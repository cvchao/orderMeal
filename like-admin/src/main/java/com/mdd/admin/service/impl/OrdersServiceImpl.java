package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IOrdersServcice;
import com.mdd.admin.validate.OrdersSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.orders.OrdersListVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.orders.Orders;
import com.mdd.common.mapper.orders.OrdersMapper;
import com.mdd.common.util.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrdersServiceImpl implements IOrdersServcice {
    @Resource
    OrdersMapper ordersMapper;


    @Override
    public PageResult<OrdersListVo> list(PageValidate pageValidate, OrdersSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        ordersMapper.setSearch(queryWrapper, searchValidate, new String[]{
                "=:number:str",
                "=:deskId@desk_id:int",
                "=:userId@user_id:int",
                "=:type:int",
                "=:status:int",
                "=:remark:str",
                "=:amount:str",
                "=:checkoutTime@checkout_time:int",
        });

        IPage<Orders> iPage = ordersMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<OrdersListVo> list = new LinkedList<>();
        for(Orders item : iPage.getRecords()) {
            OrdersListVo vo = new OrdersListVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(TimeUtils.timestampToDate(item.getCreateTime()));
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }
}
