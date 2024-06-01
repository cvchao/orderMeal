package com.mdd.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.service.IChannelOaMenusService;
import com.mdd.common.exception.OperateException;
import com.mdd.common.util.*;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpMenuServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ChannelOaMenusServiceImpl implements IChannelOaMenusService {

    /**
     * 菜单详情
     *
     * @author fzr
     * @return JSONArray
     */
    @Override
    public JSONArray detail() {
        String json = ConfigUtils.get("oa_channel", "menus", "[]");
        return JSONArray.parseArray(json);
    }

    /**
     * 菜单保存
     *
     * @author fzr
     * @param objs 参数
     * @param isPublish 是否发布
     */
    @Override
    public void save(List<Object> objs, Boolean isPublish) {
        if (objs.size() > 3) {
            throw new OperateException(MessageUtils.message("first.level.menu.exceeds.limit.(maximum.of.3)"));
        }

        List<Map<String, String>> params = new LinkedList<>();
        for (Object o : objs) {
            params.add(ToolsUtils.objectToMap(o));
        }

        List<WxMenuButton> menuButtons = new LinkedList<>();
        for (Map<String, String> item : params) {
            // 一级菜单
            Assert.notNull(item.get("name"), MessageUtils.message("the.first.level.menu.name.cannot.be.empty"));
            WxMenuButton wxMenuButton = new WxMenuButton();
            wxMenuButton.setName(item.get("name"));
            if (Integer.parseInt(item.get("menuType")) == 1) {
                Assert.notNull(item.get("visitType"), MessageUtils.message("missing.visitTypes.in.the.first.level.menu"));
                if (item.get("visitType").equals("miniprogram")) {
                    Assert.notNull(item.get("appId"), MessageUtils.message("the.first.level.menu.appId.parameter.is.missing"));
                    Assert.notNull(item.get("url"), MessageUtils.message("missing.number.of.URLs.in.the.first.level.menu"));
                    Assert.notNull(item.get("pagePath"), MessageUtils.message("missing.number.of.pagePath.in.the.first.level.menu"));
                    wxMenuButton.setType(item.get("visitType"));
                    wxMenuButton.setAppId(item.get("appId"));
                    wxMenuButton.setUrl(item.get("url"));
                    wxMenuButton.setPagePath(item.get("pagePath"));
                } else {
                    Assert.notNull(item.get("url"), MessageUtils.message("missing.number.of.URLs.in.the.first.level.menu"));
                    wxMenuButton.setType(item.get("visitType"));
                    wxMenuButton.setUrl(item.get("url"));
                    wxMenuButton.setAppId(item.getOrDefault("appId", ""));
                }
                menuButtons.add(wxMenuButton);
            }

             // 子级菜单
            if (Integer.parseInt(item.get("menuType")) == 2) {
                Assert.notNull(item.get("subButtons"), MessageUtils.message("the.submenu.cannot.be.empty"));
                List<Map<String, String>> subButtons = ArrayUtils.stringToListAsMapStr(item.get("subButtons"));

                if (subButtons.size() > 5) {
                    throw new OperateException(MessageUtils.message("subLevel.menu.exceeds.limit.(maximum.of.5)"));
                }
                for (Map<String, String> subItem : subButtons) {
                    WxMenuButton subMenuButton = new WxMenuButton();
                    Assert.notNull(subItem.get("visitType"), MessageUtils.message("missing.visitTypes.in.the.first.level.menu"));
                    if (subItem.get("visitType").equals("miniprogram")) {
                        Assert.notNull(subItem.get("appId"), MessageUtils.message("the.sub.menu.appId.parameter.is.missing"));
                        Assert.notNull(subItem.get("url"), MessageUtils.message("the.sub.menu.url.parameter.is.missing"));
                        Assert.notNull(subItem.get("pagePath"), MessageUtils.message("the.sub.menu.pagePath.parameter.is.missing"));
                        wxMenuButton.setType(subItem.get("visitType"));
                        wxMenuButton.setAppId(subItem.get("appId"));
                        wxMenuButton.setUrl(subItem.get("url"));
                        wxMenuButton.setPagePath(subItem.get("pagePath"));
                    } else {
                        Assert.notNull(subItem.get("url"), MessageUtils.message("the.sub.menu.url.parameter.is.missing"));
                        wxMenuButton.setType(subItem.get("visitType"));
                        wxMenuButton.setUrl(subItem.get("url"));
                    }
                    menuButtons.add(subMenuButton);
                }
            }
        }

        ConfigUtils.set("oa_channel", "menus", JSON.toJSONString(objs));

        if (isPublish) {
            try {
                WxMenu wxMenu = new WxMenu();
                wxMenu.setButtons(menuButtons);

                WxMpService wxMpService = WeChatUtils.official();
                WxMpMenuService wxMpMenuService = new WxMpMenuServiceImpl(wxMpService);
                wxMpMenuService.menuCreate(wxMenu);
            } catch (WxErrorException e) {
                throw new OperateException(e.getError().getErrorCode() + ": " + e.getError().getErrorMsg());
            }
        }
    }

}
