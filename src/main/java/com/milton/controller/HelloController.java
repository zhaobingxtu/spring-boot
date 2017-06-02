package com.milton.controller;

import com.milton.dao.UserDao;
import com.milton.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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

    //config read test use @Value
    @Value("${server.port}")
    private String configServerPort;

    //config read test use Environment
    @Autowired
    private Environment env;

    @Autowired
    private UserDao userService;

    @RequestMapping("/hello")
    public Object index(){
        //log4j2 test
        logger.info("test log4j2 params {} {}", "param1", "param2");

        //json return test
        Map ret = new HashMap();
        ret.put("code", 0);
        ret.put("@Value config", "success, server port is " + configServerPort);
        ret.put("Environment config", "success, server port is " + env.getProperty("server.port"));
        return ret;
    }

    @RequestMapping("/list")
    public Object userList(){
        List<User> itemList = userService.getUserListByParams();
        return itemList;
    }

    @RequestMapping("/user/{id}")
    public Object userQuery(@PathVariable int id){
        User item = userService.getUserById(id);
        return item;
    }
}