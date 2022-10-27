package com.milton.designpattern.chain;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
public class CheckParamFilterChain extends ChainAbstractHandler {
    @Override
    void doFilter(Object paramsObject) {
        log.info("执行责任链模式中的参数校验");
    }
}
