package com.milton.designpattern.templatemethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class TemplateMethodUseService {
    @Resource
    CookTomato cookTomato;

    @Resource
    CookPotato cookPotato;

    public void cookPotato() {
        log.info("开始炒土豆");
        cookPotato.cookProcess();
    }

    public void cookTomato() {
        log.info("开始炒西红柿");
        cookTomato.cookProcess();
    }
}
