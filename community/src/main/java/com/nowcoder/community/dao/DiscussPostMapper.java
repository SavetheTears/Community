package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //userId = 0时，不拼进去sql语句，其它非零值当作是正常的拼进去。动态sql
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);
    int selectDiscussPostsRows(@Param("userId") int userId);

}
