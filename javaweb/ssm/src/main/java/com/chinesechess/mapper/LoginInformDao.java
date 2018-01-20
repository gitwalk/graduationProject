package com.chinesechess.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.chinesechess.pojo.LoginInform;

public interface LoginInformDao {

    int insert(@Param("pojo") LoginInform pojo);

    int insertList(@Param("pojos") List< LoginInform> pojo);

    List<LoginInform> select(@Param("pojo") LoginInform pojo);

    int update(@Param("pojo") LoginInform pojo);

}
