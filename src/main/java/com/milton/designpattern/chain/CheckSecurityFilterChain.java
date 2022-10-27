package com.milton.designpattern.chain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Slf4j
public class CheckSecurityFilterChain extends ChainAbstractHandler {
    @Override
    void doFilter(Object paramsObject) {
        log.info("执行责任链模式中的安全校验");
    }
}
