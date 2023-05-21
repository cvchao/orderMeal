package com.mdd.admin.controller.front;

import com.mdd.admin.service.IFrontService;
import com.mdd.admin.validate.front.FrontOrdersCreateValidate;
import com.mdd.admin.validate.order.OrdersCreateValidate;
import com.mdd.admin.vo.front.DeskVo;
import com.mdd.admin.vo.front.GoodsVo;
import com.mdd.admin.vo.shop.ShopConfigVo;
import com.mdd.common.core.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/fr")
public class FrontController {

    @Resource
    private IFrontService frontService;

    @GetMapping("/goods")
    public AjaxResult<List<GoodsVo>> goods(Integer aid){
        List<GoodsVo> goods = frontService.goods(aid);
        return AjaxResult.success(goods);
    }

    @GetMapping("/seller")
    public AjaxResult<ShopConfigVo> seller(Integer id){
        ShopConfigVo seller = frontService.seller(id);
        return AjaxResult.success(seller);
    }

    @GetMapping("/desk")
    public AjaxResult<DeskVo> desk(Integer did){
        DeskVo desk = frontService.desk(did);
        return AjaxResult.success(desk);
    }

    @PostMapping("/create")
    public AjaxResult<Object> create(@RequestBody FrontOrdersCreateValidate ordersCreateValidate){
        Integer oid = frontService.create(ordersCreateValidate);
        return AjaxResult.success(oid);
    }

    /**
     * 是否已有订单
     */
    @GetMapping("/has")
    public AjaxResult<Object> has(Integer did) {
        Object o = frontService.has(did);
        return AjaxResult.success(o);
    }
}
