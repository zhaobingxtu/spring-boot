package com.milton.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author milton.zhang
 * @description test
 * @date 2017-05-19 16:09
 */
@RestController
@RequestMapping("/test")
public class HelloController {
    Logger logger = LogManager.getLogger(this.getClass());

    @RequestMapping("/hello")
    public Object index(){
        logger.info("test log4j2 params {} {}", "param1", "param2");

        Map ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "success");
        return ret;
    }
}
