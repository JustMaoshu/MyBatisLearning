package org.mybatispractise.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatispractise.pojo.Dept;
import org.mybatispractise.pojo.Emp;

import java.util.List;

public interface EmpMapper {

    List<Emp> queryAllEmp();

    /**
     * 查询员工以及员工所对应的部门信息
     * @param eid
     * @return
     */
    Emp queryEmpAndDept(@Param("eid") Integer eid);

    /**
     * 分布查询第一步：查询员工信息
     * @param eid
     * @return
     */
    Emp queryEmpAndDeptByStep1(@Param("eid") Integer eid);

    List<Emp> queryEmpsByDid(@Param("did") Integer did);

}
