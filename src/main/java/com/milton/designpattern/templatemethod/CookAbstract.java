package com.milton.designpattern.templatemethod;

import lombok.extern.slf4j.Slf4j;

/**
 * 设计模式-模板方法模式抽象类
 */
@Slf4j
public abstract class CookAbstract {

    /**
     * 抽象出的流程
     * 相同的步骤使用父类中的实现
     * 不同的步骤使用子类实现
     */
    public final void cookProcess() {
        //第一步：倒油
        this.pourOil();
        //第二步：热油
        this.heatOil();
        //第三步：倒蔬菜
        this.pourVegetable();
        //第四步：倒调味料
        this.pourSauce();
        //第五步：翻炒
        this.fry();
    }

    private void fry() {
        log.info("翻炒");
    }

    /**
     * 菜不一样
     */
    protected abstract void pourSauce();

    /**
     * 调味料不一样
     */
    protected abstract void pourVegetable();

    private void heatOil() {
        log.info("热油");
    }

    private void pourOil() {
        log.info("倒油");
    }
}
