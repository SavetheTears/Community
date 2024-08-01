package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    // tomcat use 8080 by default
    @RequestMapping("/hello") // 声明路径
    @ResponseBody // not to return a website, but only a string
    public String sayHello(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/data") // 声明路径
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    //================================Included in 1.14==============================//

    // 最原始最底层的做法
    // 没有返回值， 因为可以通过response对象可以直接向浏览器输出任何数据
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        // 演示使用底层对象如何进行
        // 一个老迭代器, strictly read-only
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement(); //请求行
            String value = request.getHeader(name); //请求头
            //System.out.println(STR."\{name}:\{value}");
            System.out.printf("\\%s:\\%s\n",name, value);
        }
        System.out.println(request.getParameter("code"));

        // response是用来向浏览器返回响应数据的对象
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter(); //for try-clause, write in the (), automatically have finally to close.
        ){
            writer.write("<h1>nowcoding</h1>");
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    // GET（默认）： 两种传参方式。
    // /students?current=2&limit=50 display students in pages 方式1/2
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name="current", required = false,defaultValue = "1") int current, // 分页第几页
            @RequestParam(name="limit", required = false,defaultValue = "10") int limit     // 每一页最多多少条数据
    ){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /students/123, get the one student 方式2/2
    @RequestMapping(path="/student/{id}", method=RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        //return STR."student\{id}";
        return "student\\"+id;
    }

    // POST, start a static webpage in resources->static
    // /html/student.html
    @RequestMapping(path="/student", method=RequestMethod.POST)
    @ResponseBody
    public String addStudent(String name, int age){
        System.out.println(name+age);
        return "Success";
    }

    // 响应HTML数据
    // DispatcherServlet 会返回Model和视图数据，提交给模版引擎(也就是resources->templates->demo文件夹下的)
    // 不加@ResponseBody默认返回的是html
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","Kidd");
        mav.addObject("age","35");
        mav.setViewName("/demo/view");//path and name of the template, Thymeleaf default HTML
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){ //返回string就是view的路径
        model.addAttribute("name","HKUST");
        model.addAttribute("age","32");
        return("demo/view"); //返回给dispatcherServlet
    }

    // 响应JSON数据，常常用于异步请求 -- 当前网页不动（转圈圈），悄悄的访问了服务器
    // 例如网站新账号注册，检查某个昵称是否已被注册，肯定是要访问服务器来做判断的
    // JSON -- 网页用JS对象，所以需要将返回的Java对象转换为JSON字符串
    @RequestMapping(path="/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","Kick");
        emp.put("age", 30);
        emp.put("position", "Devops");
        return emp;
    }

    @RequestMapping(path="/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","Kick");
        emp.put("age", 30);
        emp.put("position", "Devops");
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","Ass");
        emp.put("age", 25);
        emp.put("position", "R&D");
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","Yeah");
        emp.put("age", 48);
        emp.put("position", "senior Stack");
        list.add(emp);

        return list;
    }
}
