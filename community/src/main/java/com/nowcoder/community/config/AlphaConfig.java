package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

// 表明为Spring配置类
@Configuration
public class AlphaConfig {
    // 定义第三的类为Bean，得用相关的注解。
    @Bean
    public SimpleDateFormat simpleDateFormat(){
        //方法名就是Bean的名字
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    }
}
