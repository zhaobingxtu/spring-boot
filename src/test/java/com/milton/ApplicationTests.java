package com.milton;

import com.milton.designpattern.chain.ChainUseService;
import com.milton.designpattern.strategy.StrategyUseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApplicationTests {
    @Resource
    StrategyUseService strategyUseService;

    @Resource
    ChainUseService chainUseService;

    /**
     * 设计模式-策略模式
     */
    @Test
    public void designPatternStrategyTest() {
        strategyUseService.parseData("XML", "testStrategy");
    }

    /**
     * 设计模式-责任链模式
     */
    @Test
    public void designPatternChainTest() {
        chainUseService.execChain("test");
    }

}
