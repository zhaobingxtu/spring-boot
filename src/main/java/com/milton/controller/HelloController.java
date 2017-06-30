package com.milton.controller;

import com.milton.dao.UserDao;
import com.milton.dao.UserMongoRepository;
import com.milton.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    //mybatis case
    @Autowired
    private UserDao userDao;

    //mongodb case
    @Autowired
    private UserMongoRepository userMongoRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        List<User> itemList = userDao.getUserListByParams();
        return itemList;
    }

    @RequestMapping("/user/{id}")
    public Object userQuery(@PathVariable int id){
        User item = userDao.getUserById(id);
        return item;
    }

    @RequestMapping("/mongo/list")
    public Object userListFromMongo(){
        return userMongoRepository.findAll();
    }

    @RequestMapping("/mongo/save/{id}")
    public Object saveUserByMongo(@PathVariable int id){
        userMongoRepository.save(new User(id, "milton"+id));
        return userMongoRepository.findAll();
    }

    @RequestMapping("/redis/get/{key}")
    public Object redisGet(@PathVariable String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    @RequestMapping("/redis/set/{key}")
    public Object redisSet(@PathVariable String key){
        stringRedisTemplate.opsForValue().set(key, key);
        return true;
    }
}
