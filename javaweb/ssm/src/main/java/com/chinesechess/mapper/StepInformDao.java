package com.chinesechess.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.chinesechess.pojo.StepInform;

public interface StepInformDao {

    int insert(@Param("pojo") StepInform pojo);

    int insertList(@Param("pojos") List< StepInform> pojo);

    List<StepInform> select(@Param("pojo") StepInform pojo);

    int update(@Param("pojo") StepInform pojo);

}
