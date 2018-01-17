package com.chinesechess.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.chinesechess.pojo.UserInform;

public interface UserInformDao {



    List<UserInform> select(@Param("pojo") UserInform pojo);

    int update(@Param("pojo") UserInform pojo);

}
