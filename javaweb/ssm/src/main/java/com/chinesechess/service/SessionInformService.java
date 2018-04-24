package com.chinesechess.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.chinesechess.pojo.SessionInform;
import com.chinesechess.mapper.SessionInformDao;

@Service
public class SessionInformService {

    @Resource
    private SessionInformDao sessionInformDao;

    public int insert(SessionInform pojo){
        return sessionInformDao.insert(pojo);
    }

    public int insertList(List< SessionInform> pojos){
        return sessionInformDao.insertList(pojos);
    }

    public List<SessionInform> select(SessionInform pojo){
        return sessionInformDao.select(pojo);
    }

    public int update(SessionInform pojo){
        return sessionInformDao.update(pojo);
    }

}
