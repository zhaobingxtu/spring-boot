package com.milton.dao;

import com.milton.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by milton.zhang on 2017/6/30 0030.
 */
public interface UserMongoRepository extends MongoRepository<User, Long> {
}
