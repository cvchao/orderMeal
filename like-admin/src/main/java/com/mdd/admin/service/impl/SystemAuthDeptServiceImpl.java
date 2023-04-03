package com.mdd.admin.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.service.ISystemAuthDeptService;
import com.mdd.admin.validate.system.SystemDeptCreateValidate;
import com.mdd.admin.validate.system.SystemDeptSearchValidate;
import com.mdd.admin.validate.system.SystemDeptUpdateValidate;
import com.mdd.admin.vo.system.SystemAuthDeptVo;
import com.mdd.common.entity.system.SystemAuthAdmin;
import com.mdd.common.entity.system.SystemAuthDept;
import com.mdd.common.mapper.system.SystemAuthAdminMapper;
import com.mdd.common.mapper.system.SystemAuthDeptMapper;
import com.mdd.common.util.ArrayUtils;
import com.mdd.common.util.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统部门服务实现类
 */
@Service
class SystemAuthDeptServiceImpl implements ISystemAuthDeptService {

    @Resource
    SystemAuthDeptMapper systemAuthDeptMapper;


    /**
     * 岗位所有
     *
     * @author fzr
     * @return List<SystemAuthDeptVo>
     */
    @Override
    public List<SystemAuthDeptVo> all() {
        List<SystemAuthDept> systemAuthDeptList = systemAuthDeptMapper.selectList(new QueryWrapper<SystemAuthDept>()
                .gt("aid", LikeAdminThreadLocal.getAdminId())
                .eq("is_delete", 0)
                .orderByDesc((Arrays.asList("sort", "id"))));

        List<SystemAuthDeptVo> list = new ArrayList<>();
        for (SystemAuthDept systemAuthDept : systemAuthDeptList) {
            SystemAuthDeptVo vo = new SystemAuthDeptVo();
            BeanUtils.copyProperties(systemAuthDept, vo);

            vo.setUpdateTime(TimeUtils.timestampToDate(systemAuthDept.getUpdateTime()));
            vo.setCreateTime(TimeUtils.timestampToDate(systemAuthDept.getCreateTime()));
            list.add(vo);
        }

        return list;
    }

    /**
     *  部门列表
     *
     * @author fzr
     * @param searchValidate 搜索参数
     * @return JSONArray
     */
    @Override
    public JSONArray list(SystemDeptSearchValidate searchValidate) {
        QueryWrapper<SystemAuthDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0).eq("aid",LikeAdminThreadLocal.getAdminId());
        queryWrapper.orderByDesc(Arrays.asList("sort", "id"));
        queryWrapper.select(SystemAuthDept.class, info ->
                !info.getColumn().equals("is_delete") &&
                !info.getColumn().equals("delete_time"));

        systemAuthDeptMapper.setSearch(queryWrapper, searchValidate, new String[]{
                "like:name:str",
                "=:isStop@is_stop:int"
        });

        List<SystemAuthDept> systemAuthDeptList = systemAuthDeptMapper.selectList(queryWrapper);

        List<SystemAuthDeptVo> list = new LinkedList<>();
        for (SystemAuthDept systemAuthDept : systemAuthDeptList) {
            SystemAuthDeptVo vo = new SystemAuthDeptVo();
            BeanUtils.copyProperties(systemAuthDept, vo);

            vo.setCreateTime(TimeUtils.timestampToDate(systemAuthDept.getCreateTime()));
            vo.setUpdateTime(TimeUtils.timestampToDate(systemAuthDept.getUpdateTime()));
            list.add(vo);
        }

        return JSONArray.parseArray(JSONArray.toJSONString(list));
    }

    /**
     * 部门详情
     *
     * @author fzr
     * @param id 主键
     * @return SystemDeptVo
     */
    @Override
    public SystemAuthDeptVo detail(Integer id) {
        SystemAuthDept systemAuthDept = systemAuthDeptMapper.selectOne(
                new QueryWrapper<SystemAuthDept>()
                        .select(SystemAuthDept.class, info ->
                                !info.getColumn().equals("is_delete") &&
                                        !info.getColumn().equals("delete_time"))
                        .eq("id", id)
                        .eq("is_delete", 0)
                        .last("limit 1"));

        Assert.notNull(systemAuthDept, "部门已不存在!");

        SystemAuthDeptVo vo  = new SystemAuthDeptVo();
        BeanUtils.copyProperties(systemAuthDept, vo);
        vo.setCreateTime(TimeUtils.timestampToDate(systemAuthDept.getCreateTime()));
        vo.setUpdateTime(TimeUtils.timestampToDate(systemAuthDept.getUpdateTime()));

        return vo;
    }

    /**
     * 部门新增
     *
     * @author fzr
     * @param createValidate 参数
     */
    @Override
    public void add(SystemDeptCreateValidate createValidate) {
        SystemAuthDept model = new SystemAuthDept();
        model.setAid(LikeAdminThreadLocal.getAdminId());
        model.setAreaId(createValidate.getAreaId());
        model.setName(createValidate.getName());
        model.setDuty(createValidate.getDuty());
        model.setMobile(createValidate.getMobile());
        model.setSort(createValidate.getSort());
        model.setIsStop(createValidate.getIsStop());
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthDeptMapper.insert(model);
    }

    /**
     * 部门编辑
     *
     * @author fzr
     * @param updateValidate 参数
     */
    @Override
    public void edit(SystemDeptUpdateValidate updateValidate) {
        SystemAuthDept model = systemAuthDeptMapper.selectOne(
                new QueryWrapper<SystemAuthDept>()
                        .select(SystemAuthDept.class, info ->
                           !info.getColumn().equals("is_delete") &&
                           !info.getColumn().equals("delete_time"))
                        .eq("id", updateValidate.getId())
                        .eq("is_delete", 0)
                        .last("limit 1"));

        Assert.notNull(model, "部门不存在");

        model.setName(updateValidate.getName());
        model.setDuty(updateValidate.getDuty());
        model.setMobile(updateValidate.getMobile());
        model.setSort(updateValidate.getSort());
        model.setIsStop(updateValidate.getIsStop());
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthDeptMapper.updateById(model);
    }

    /**
     * 部门删除
     *
     * @author fzr
     * @param id 主键
     */
    @Override
    public void del(Integer id) {
        SystemAuthDept model = systemAuthDeptMapper.selectOne(
                new QueryWrapper<SystemAuthDept>()
                        .select("id,area_id,name")
                        .eq("id", id)
                        .eq("is_delete", 0)
                        .last("limit 1"));

        Assert.notNull(model, "部门不存在");

        model.setIsDelete(1);
        model.setDeleteTime(System.currentTimeMillis() / 1000);
        systemAuthDeptMapper.updateById(model);
    }

}