package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.service.IOrderService;
import com.mdd.admin.vo.order.OrderDeskVo;
import com.mdd.admin.vo.order.OrderDishCateVo;
import com.mdd.admin.vo.order.OrderDishVo;
import com.mdd.common.config.GlobalConfig;
import com.mdd.common.entity.article.Article;
import com.mdd.common.entity.article.ArticleCategory;
import com.mdd.common.entity.system.SystemAuthDept;
import com.mdd.common.mapper.article.ArticleCategoryMapper;
import com.mdd.common.mapper.article.ArticleMapper;
import com.mdd.common.mapper.system.SystemAuthDeptMapper;
import com.mdd.common.util.TimeUtils;
import com.mdd.common.util.UrlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private SystemAuthDeptMapper systemAuthDeptMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<OrderDeskVo> list() {
        Integer aid = LikeAdminThreadLocal.getAdminId();

        MPJQueryWrapper<SystemAuthDept> mpjQueryWrapper = new MPJQueryWrapper<SystemAuthDept>()
                .selectAll(SystemAuthDept.class)
                .select("post.name as area")
                .innerJoin("?_system_auth_post post ON post.id=t.area_id".replace("?_", GlobalConfig.tablePrefix))
                .eq("t.is_delete", 0)
                .eq("t.is_stop",0)
                .eq("t.aid", aid)
                .orderByDesc(Arrays.asList("t.sort", "t.id"));

        List<OrderDeskVo> orderDeskVos = systemAuthDeptMapper.selectJoinList(OrderDeskVo.class, mpjQueryWrapper);
        for (OrderDeskVo orderDeskVo : orderDeskVos) {
            orderDeskVo.setCreateTime(TimeUtils.timestampToDate(orderDeskVo.getCreateTime()));
            orderDeskVo.setUpdateTime(TimeUtils.timestampToDate(orderDeskVo.getUpdateTime()));
        }
        return orderDeskVos;
    }

    @Override
    public List<OrderDishCateVo> dishCate() {
        Integer aid = LikeAdminThreadLocal.getAdminId();
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        queryWrapper.eq("is_delete",0);
        queryWrapper.eq("is_show",1);
        List<ArticleCategory> articleCategories = articleCategoryMapper.selectList(queryWrapper);
        List<OrderDishCateVo> orderDishCateVos = new ArrayList<>();
        for (ArticleCategory articleCategory : articleCategories){
            OrderDishCateVo orderDishCateVo = new OrderDishCateVo();
            orderDishCateVo.setName(articleCategory.getName());
            orderDishCateVo.setSort(articleCategory.getSort());
            orderDishCateVo.setId(articleCategory.getId());
            orderDishCateVos.add(orderDishCateVo);
        }
        return orderDishCateVos;

    }

    @Override
    public List<OrderDishVo> dishList() {
        Integer aid = LikeAdminThreadLocal.getAdminId();

        MPJQueryWrapper<Article> mpjQueryWrapper = new MPJQueryWrapper<Article>()
                .selectAll(Article.class)
                .select("ac.name as category")
                .innerJoin("?_article_category ac ON ac.id=t.cid".replace("?_", GlobalConfig.tablePrefix))
                .eq("t.is_delete", 0)
                .eq("t.aid",aid)
                .eq("t.is_show",1)
                .orderByDesc(Arrays.asList("t.sort", "t.id"));

        List<OrderDishVo> orderDishVos = articleMapper.selectJoinList(OrderDishVo.class, mpjQueryWrapper);
        for (OrderDishVo orderDishVo : orderDishVos){
            orderDishVo.setImage(UrlUtils.toAbsoluteUrl(orderDishVo.getImage()));
            orderDishVo.setCreateTime(TimeUtils.timestampToDate(orderDishVo.getCreateTime()));
            orderDishVo.setUpdateTime(TimeUtils.timestampToDate(orderDishVo.getUpdateTime()));
        }
        return orderDishVos;

    }
}
