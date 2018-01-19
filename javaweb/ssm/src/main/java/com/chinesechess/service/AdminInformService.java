package com.chinesechess.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.chinesechess.pojo.AdminInform;
import com.chinesechess.mapper.AdminInformDao;

@Service
public class AdminInformService {

    @Resource
    private AdminInformDao adminInformDao;

    public int insert(AdminInform pojo){
        return adminInformDao.insert(pojo);
    }

    public int insertList(List< AdminInform> pojos){
        return adminInformDao.insertList(pojos);
    }

    public List<AdminInform> select(AdminInform pojo){
        return adminInformDao.select(pojo);
    }

    public int update(AdminInform pojo){
        return adminInformDao.update(pojo);
    }

}
