package com.milton.controller;

import com.milton.designpattern.strategy.StrategyUseService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 设计模式 test
 */
@RestController
@RequestMapping("design")
public class DesignPatternController {

    @Resource
    StrategyUseService strategyUseService;

    @GetMapping("strategy")
    public Object strategy(String dataType) {
        Assert.hasText(dataType, "数据类型不能为空");
        strategyUseService.parseData(dataType, "testJson");
        return "策略模式执行完成";
    }
}
