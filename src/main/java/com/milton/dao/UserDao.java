package com.milton.dao;

import com.milton.entity.User;
import com.milton.util.MyMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by milton.zhang on 2017/5/22 0022.
 * 测试Mybatis Dao
 */
@CacheConfig(cacheNames = "users")
public interface UserDao extends MyMapper<User>{

    //use xml config
    @Cacheable
    User getUserById(int id);

    //use code sql
    @Select("select id, loginname from t_user")
    List<User> getUserListByParams();
}
