package com.chinesechess.service;

import com.chinesechess.pojo.StepInform;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.chinesechess.mapper.StepInformDao;

@Service
public class StepInformService {

    @Resource
    private StepInformDao stepInformDao;

    public int insert(StepInform pojo){
        return stepInformDao.insert(pojo);
    }

    public int insertList(List< StepInform> pojos){
        return stepInformDao.insertList(pojos);
    }

    public List<StepInform> select(StepInform pojo){
        return stepInformDao.select(pojo);
    }

    public int update(StepInform pojo){
        return stepInformDao.update(pojo);
    }

}
