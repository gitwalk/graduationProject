package com.chinesechess.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.chinesechess.pojo.GameInform;

public interface GameInformDao {

    int insert(@Param("pojo") GameInform pojo);

    int insertList(@Param("pojos") List< GameInform> pojo);

    List<GameInform> select(@Param("pojo") GameInform pojo);

    int update(@Param("pojo") GameInform pojo);

}
