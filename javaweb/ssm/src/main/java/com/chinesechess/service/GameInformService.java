package com.chinesechess.service;

import com.chinesechess.pojo.GameInform;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.chinesechess.mapper.GameInformDao;

@Service
public class GameInformService {

    @Resource
    private GameInformDao gameInformDao;

    public int insert(GameInform pojo){
        return gameInformDao.insert(pojo);
    }

    public int insertList(List< GameInform> pojos){
        return gameInformDao.insertList(pojos);
    }

    public List<GameInform> select(GameInform pojo){
        return gameInformDao.select(pojo);
    }

    public int update(GameInform pojo){
        return gameInformDao.update(pojo);
    }

}
