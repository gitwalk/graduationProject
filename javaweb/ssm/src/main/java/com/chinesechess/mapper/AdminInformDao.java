package com.chinesechess.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.chinesechess.pojo.AdminInform;

public interface AdminInformDao {

    int insert(@Param("pojo") AdminInform pojo);

    int insertList(@Param("pojos") List< AdminInform> pojo);

    List<AdminInform> select(@Param("pojo") AdminInform pojo);

    int update(@Param("pojo") AdminInform pojo);

}
