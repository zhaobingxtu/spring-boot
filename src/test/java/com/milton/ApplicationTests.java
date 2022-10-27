package com.milton;

import com.milton.designpattern.strategy.StrategyUseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
public class ApplicationTests {
	@Resource
	StrategyUseService strategyUseService;

	@Test
	public void designPatternStrategyTest() {
		strategyUseService.parseData("XML", "testStrategy");
	}

}
