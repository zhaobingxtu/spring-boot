package com.milton;

import com.milton.designpattern.chain.ChainUseService;
import com.milton.designpattern.observer.ObserverUseServer;
import com.milton.designpattern.strategy.StrategyUseService;
import com.milton.designpattern.templatemethod.TemplateMethodUseService;
import com.milton.entity.User;
import com.milton.entity.UserDTO;
import com.milton.entity.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApplicationTests {
    @Resource
    StrategyUseService strategyUseService;

    @Resource
    ChainUseService chainUseService;

    @Resource
    TemplateMethodUseService templateMethodUseService;

    @Resource
    ObserverUseServer observerUseServer;

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

    /**
     * 设计模式-模板方法模式
     */
    @Test
    public void designPatternTemplateMethodTest() {
        templateMethodUseService.cookPotato();
        templateMethodUseService.cookTomato();
    }

    /**
     * 设计模式-观察者模式
     */
    @Test
    public void designPatternObserverTest() {
        observerUseServer.mouseGoOut();
    }

    @Test
    public void mapStructTest() {
        User user = new User(1, "zhangsan");
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);
        System.out.println(userDTO);
    }
}
