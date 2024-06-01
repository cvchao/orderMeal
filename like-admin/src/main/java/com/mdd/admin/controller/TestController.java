package com.mdd.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.mdd.common.util.MessageUtils;
import com.mdd.common.validator.annotation.FreeTokenAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/test")
    @FreeTokenAnnotation
    public String test1(String msg){
        return MessageUtils.message("unsupported.extensions",10);
    }
}
