package com.milton.designpattern.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者-猫
 */
@Slf4j
public class CatObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        log.info("老鼠出来了，快去抓，通知参数[{}]", arg);
    }
}
