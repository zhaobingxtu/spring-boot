package com.milton.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class StrategyUseService implements ApplicationContextAware {

    private Map<String, IDataParseStrategy> iDataParseStrategyMap = new ConcurrentHashMap<>();

    /**
     * 数据解析入口，从map中获取对应的实现策略去执行
     *
     * @param dataType
     * @param dataObject
     */
    public void parseData(String dataType, Object dataObject) {
        IDataParseStrategy iDataParseStrategy = iDataParseStrategyMap.get(dataType);
        if (iDataParseStrategy != null) {
            iDataParseStrategy.dataParse(dataObject);
        } else {
            log.error("数据类型[{}]的解析策略实现不存在！", dataType);
        }
    }

    /**
     * 将不同策略放入map
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IDataParseStrategy> tempMap = applicationContext.getBeansOfType(IDataParseStrategy.class);
        tempMap.values().forEach(e -> iDataParseStrategyMap.put(e.dataType(), e));
        log.info("数据解析策略初始化完成");
    }
}
