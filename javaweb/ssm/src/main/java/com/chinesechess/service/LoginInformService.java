package com.chinesechess.service;

import com.chinesechess.pojo.LoginInform;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.chinesechess.mapper.LoginInformDao;

@Service
public class LoginInformService {

    @Resource
    private LoginInformDao loginInformDao;

    public int insert(LoginInform pojo){
        return loginInformDao.insert(pojo);
    }

    public int insertList(List< LoginInform> pojos){
        return loginInformDao.insertList(pojos);
    }

    public List<LoginInform> select(LoginInform pojo){
        return loginInformDao.select(pojo);
    }

    public int update(LoginInform pojo){
        return loginInformDao.update(pojo);
    }

}
