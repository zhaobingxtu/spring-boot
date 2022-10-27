package com.milton.designpattern.chain;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class ChainUseService {

    @Resource
    private List<ChainAbstractHandler> chainAbstractHandlerList;

    private ChainAbstractHandler chainAbstractHandler;

    @PostConstruct
    public void initializeChainFilter() {
        for (int i = 0; i < chainAbstractHandlerList.size(); i++) {
            if (i == 0) {
                chainAbstractHandler = chainAbstractHandlerList.get(0);
            } else {
                ChainAbstractHandler currentHander = chainAbstractHandlerList.get(i - 1);
                ChainAbstractHandler nextHander = chainAbstractHandlerList.get(i);
                currentHander.setNextHandler(nextHander);
            }
        }
    }

    /**
     * 责任链使用入口
     * @param paramsObject
     * @return
     */
    public String execChain(Object paramsObject) {
        chainAbstractHandler.filter(paramsObject);
        log.info("责任链执行完成");
        return "责任链执行完成";
    }

    public ChainAbstractHandler getChainAbstractHandler() {
        return chainAbstractHandler;
    }

    public void setChainAbstractHandler(ChainAbstractHandler chainAbstractHandler) {
        this.chainAbstractHandler = chainAbstractHandler;
    }
}
