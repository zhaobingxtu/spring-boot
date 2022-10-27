package com.milton.designpattern.templatemethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 炒土豆
 */
@Slf4j
@Component
public class CookPotato extends CookAbstract{
    @Override
    protected void pourSauce() {
        log.info("放土豆的调味料");
    }

    @Override
    protected void pourVegetable() {
        log.info("放土豆");
    }
}
