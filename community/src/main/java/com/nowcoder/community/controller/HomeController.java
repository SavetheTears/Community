package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    // 响应的是网页html，没必要写@ResponseBody了
    @RequestMapping(path="/index", method= RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        // 方法调用前,SpringMVC会通过DispatchServlet自动实例化Model和Page,并将Page注入Model.
        // 所以,在thymeleaf中可以直接访问Page对象中的数据.
        page.setRows(discussPostService.findDiscussPostRowsById(0));
        page.setPath("/index");

        List<DiscussPost> postList = discussPostService.findDiscussPost(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(postList!=null){
            for(DiscussPost post: postList) {
                Map<String, Object> record = new HashMap<>();
                record.put("post", post);
                record.put("user", userService.findUserById(post.getUserId()));
                discussPosts.add(record);
            }
        }
        //得到的结果要装到model里面，页面才能得到
        model.addAttribute("discussPosts", discussPosts);
        /*
            这一步不是必须的，SpringMVC会自动把Page注入到Model里面
            model.addAttribute("page", page);

         */
        return "/index";
    }
}
