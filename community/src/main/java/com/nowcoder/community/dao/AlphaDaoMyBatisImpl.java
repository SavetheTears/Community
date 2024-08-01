package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
@Primary // 避免歧义
@Repository("alphaMyBatis")
public class AlphaDaoMyBatisImpl implements AlphaDao{
    @Override
    public String select(){
        return "MyBatis Bean";
    }
}
