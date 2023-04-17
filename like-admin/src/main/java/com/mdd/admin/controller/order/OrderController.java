package com.mdd.admin.controller.order;

import com.mdd.admin.service.IOrderService;
import com.mdd.admin.vo.order.OrderDeskVo;
import com.mdd.admin.vo.order.OrderDishCateVo;
import com.mdd.admin.vo.order.OrderDishVo;
import com.mdd.common.core.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private IOrderService iOrderService;


    @GetMapping("/list")
    public AjaxResult<List<OrderDeskVo>> listDesk(){
        List<OrderDeskVo> list = iOrderService.list();
        return AjaxResult.success(list);
    }

    @GetMapping("/dish/cate")
    public AjaxResult<List<OrderDishCateVo>> dishCate(){
        List<OrderDishCateVo> orderDishCateVos = iOrderService.dishCate();
        return AjaxResult.success(orderDishCateVos);
    }

    @GetMapping("/dish/list")
    public AjaxResult<List<OrderDishVo>> dishList(){
        List<OrderDishVo> orderDishVos = iOrderService.dishList();
        return AjaxResult.success(orderDishVos);
    }
}
