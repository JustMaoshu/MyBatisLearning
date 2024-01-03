package org.mybatispractise.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatispractise.pojo.Dept;

public interface DeptMapper {

    Dept queryDeptByDid(@Param("did") Integer did);

    Dept queryDeptAndEmp(@Param("did") Integer did);

    //第一步，查询部门信息
    Dept queryDeptAndEmpStep1(@Param("did") Integer did);

}
