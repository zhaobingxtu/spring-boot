package com.milton.designpattern.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ObserverUseServer {
    @Resource
    MouseObservable mouseObservable;

    public void mouseGoOut() {
        CatObserver cat = new CatObserver();
        //添加观察者-猫
        mouseObservable.addObserver(cat);
        mouseObservable.goOut();
    }
}
