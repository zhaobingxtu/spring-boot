package com.milton.designpattern.strategy;

/**
 * 设计模式-策略模式接口
 * 数据解析策略
 */
public interface IDataParseStrategy {
    /**
     * 数据类型
     *
     * @return
     */
    String dataType();

    /**
     * 数据解析
     *
     * @param dataObject
     */
    void dataParse(Object dataObject);
}
