package com.chinesechess.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.chinesechess.pojo.UserInform;

public interface UserInformDao {


    int insert(@Param("pojo") UserInform pojo);

    int insertList(@Param("pojos") List< UserInform> pojo);

    List<UserInform> select(@Param("pojo") UserInform pojo);

    int update(@Param("pojo") UserInform pojo);

}
