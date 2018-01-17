package com.chinesechess.mapper;

import com.chinesechess.pojo.Test;

import java.util.List;

public interface TestMapper {

    public void add(Test category);

    public void delete(int id);

    public Test get(int id);

    public void update(Test category);

    public List<Test> list();

    public int count();

}