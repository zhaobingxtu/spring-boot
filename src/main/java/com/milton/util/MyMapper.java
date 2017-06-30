package com.milton.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
/**
 * Created by milton.zhang on 2017/6/30 0030.
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>  {
}
