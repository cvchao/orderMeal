package com.mdd.admin.controller.orders;

import com.mdd.admin.service.IOrdersServcice;
import com.mdd.admin.validate.OrdersSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.orders.OrdersListVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Resource
    private IOrdersServcice ordersServcice;

    /**
     * 【请填写功能名称】列表
     *
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return AjaxResult<Object>
     */
    @GetMapping("/list")
    public AjaxResult<PageResult<OrdersListVo>> list(@Validated PageValidate pageValidate, @Validated OrdersSearchValidate searchValidate){
        PageResult<OrdersListVo> voPageResult = ordersServcice.list(pageValidate, searchValidate);
        return AjaxResult.success(voPageResult);
    }
}
