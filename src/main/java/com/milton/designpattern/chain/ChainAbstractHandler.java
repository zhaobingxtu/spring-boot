package com.milton.designpattern.chain;

/**
 * 设计模式-责任连模式抽象类
 */
public abstract class ChainAbstractHandler {

    /**
     * 责任链中的下一个对象
     */
    private ChainAbstractHandler nextHandler;

    /**
     * 设置责任链的下一个对象
     *
     * @param nextHandler
     */
    public void setNextHandler(ChainAbstractHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public ChainAbstractHandler getNextHandler() {
        return nextHandler;
    }

    /**
     * 具体参数拦截逻辑,给子类去实现
     *
     * @param paramsObject
     */
    public void filter(Object paramsObject) {
        doFilter(paramsObject);
        if (getNextHandler() != null) {
            getNextHandler().filter(paramsObject);
        }
    }

    void doFilter(Object paramsObject) {

    }
}
