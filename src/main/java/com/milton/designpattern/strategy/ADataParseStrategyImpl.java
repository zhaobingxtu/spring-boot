package com.milton.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ADataParseStrategyImpl implements IDataParseStrategy {
    @Override
    public String dataType() {
        return "JSON";
    }

    @Override
    public void dataParse(Object dataObject) {
        log.info("执行数据类型[{}]的数据解析方法", dataType());
    }
}
