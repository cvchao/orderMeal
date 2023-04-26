package com.mdd.admin.controller.order;

import com.mdd.admin.service.IOrderService;
import com.mdd.admin.validate.order.DishAddValidate;
import com.mdd.admin.validate.order.OrderSubmitValidate;
import com.mdd.admin.validate.order.OrdersCreateValidate;
import com.mdd.admin.vo.order.OrderDeskVo;
import com.mdd.admin.vo.order.OrderDishCateVo;
import com.mdd.admin.vo.order.OrderDishVo;
import com.mdd.common.core.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private IOrderService iOrderService;

    /**
     * 全部餐桌
     * @return
     */
    @GetMapping("/list")
    public AjaxResult<List<OrderDeskVo>> listDesk(){
        List<OrderDeskVo> list = iOrderService.list();
        return AjaxResult.success(list);
    }

    /**
     * 菜品分类
     * @return
     */
    @GetMapping("/dish/cate")
    public AjaxResult<List<OrderDishCateVo>> dishCate(){
        List<OrderDishCateVo> orderDishCateVos = iOrderService.dishCate();
        return AjaxResult.success(orderDishCateVos);
    }

    /**
     * 全部菜品
     * @return
     */
    @GetMapping("/dish/list")
    public AjaxResult<List<OrderDishVo>> dishList(){
        List<OrderDishVo> orderDishVos = iOrderService.dishList();
        return AjaxResult.success(orderDishVos);
    }
    /**
     * 创建订单
     */
    @PostMapping("/create")
    public AjaxResult<Object> ordersCreate(@RequestBody OrdersCreateValidate ordersCreateValidate){
        Integer number = iOrderService.ordersCreate(ordersCreateValidate);
        return AjaxResult.success("success",number);//返回订单号
    }
    /**
     * 菜品添加
     */
    @GetMapping("/dishAdd")
    public AjaxResult<Object> dishAdd(DishAddValidate dishAddValidate){
        iOrderService.dishAdd(dishAddValidate);
        return AjaxResult.success();
    }

    /**
     * 下单
     */
    @GetMapping("/submit")
    public AjaxResult<Object> submit(@RequestBody OrderSubmitValidate orderSubmitValidate){
        iOrderService.submit(orderSubmitValidate);
        return AjaxResult.success();
    }

}
