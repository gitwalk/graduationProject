package com.chinesechess.service;

import com.chinesechess.pojo.UserInform;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.chinesechess.mapper.UserInformDao;

@Service
public class UserInformService {

    @Resource
    private UserInformDao userInformDao;


    public List<UserInform> select(UserInform pojo){
        return userInformDao.select(pojo);
    }

    public int update(UserInform pojo){
        return userInformDao.update(pojo);
    }

    public int insert(UserInform pojo){
        return userInformDao.insert(pojo);
    }

}
