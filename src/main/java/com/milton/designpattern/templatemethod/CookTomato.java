package com.milton.designpattern.templatemethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 炒西红柿
 */
@Slf4j
@Component
public class CookTomato extends CookAbstract {
    @Override
    protected void pourSauce() {
        log.info("放西红柿的调味料");
    }

    @Override
    protected void pourVegetable() {
        log.info("放西红柿");
    }
}
