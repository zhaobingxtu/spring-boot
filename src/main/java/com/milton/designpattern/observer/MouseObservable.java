package com.milton.designpattern.observer;

import org.springframework.stereotype.Component;

import java.util.Observable;

/**
 * 被观察者-老鼠
 */
@Component
public class MouseObservable extends Observable {
    public void goOut() {
        this.setChanged();
        notifyObservers("老鼠出洞了");
    }
}
