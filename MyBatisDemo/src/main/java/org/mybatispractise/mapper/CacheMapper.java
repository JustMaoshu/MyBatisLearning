package org.mybatispractise.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatispractise.pojo.Emp;

public interface CacheMapper {

    Emp queryEmpByEid(@Param("eid") Integer eid);

}
