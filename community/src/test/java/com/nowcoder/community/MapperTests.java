package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes= CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);

    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("testest");
        user.setPassword("123456");
        user.setSalt("abs");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.newcoder.com/109.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdateUser(){
        int rows = userMapper.updateStatus(150, 1);
        System.out.println(rows);

        rows = userMapper.updatePassword(150, "654321");
        System.out.println(rows);

        rows = userMapper.updateHeader(150, "http://www.newcoder.com/88.png");
        System.out.println(rows);

    }

    @Test
    public void testSelectPost(){
        List<DiscussPost> discussPostList = discussPostMapper.selectDiscussPosts(101,0,10);
        for(DiscussPost dp:discussPostList){
            System.out.println(dp);
        }
    }

    @Test
    public void testSelectPostRows(){
        int rows = discussPostMapper.selectDiscussPostsRows(149);
        System.out.println(rows);
    }


}
