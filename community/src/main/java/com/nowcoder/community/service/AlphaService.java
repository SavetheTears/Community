package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

// 业务类，希望由容器进行管理
@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService(){
        System.out.println("AlphaService Constructor Called");
    }

    @PostConstruct
    public void init(){
        System.out.println("AlphaService gets Initialized");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("AlphaService object Destroyed");

    }

    public String find(){
        return alphaDao.select();
    }
}
