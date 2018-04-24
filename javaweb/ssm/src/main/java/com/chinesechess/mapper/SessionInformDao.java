package com.chinesechess.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.chinesechess.pojo.SessionInform;

public interface SessionInformDao {

    int insert(@Param("pojo") SessionInform pojo);

    int insertList(@Param("pojos") List< SessionInform> pojo);

    List<SessionInform> select(@Param("pojo") SessionInform pojo);

    int update(@Param("pojo") SessionInform pojo);

}
