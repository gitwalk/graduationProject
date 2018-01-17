package com.chinesechess.service.impl;

import com.chinesechess.mapper.TestMapper;
import com.chinesechess.pojo.Test;
import com.chinesechess.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl  implements TestService{
    @Autowired
    TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list();
    };

}