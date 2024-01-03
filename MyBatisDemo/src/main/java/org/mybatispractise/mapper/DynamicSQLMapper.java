package org.mybatispractise.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatispractise.pojo.Emp;

import java.util.List;

public interface DynamicSQLMapper {

    /**
     * 多条件查询
     */
    List<Emp> queryEmpByCondition(Emp emp);

    List<Emp> queryEmpByCondition2(Emp emp);

    List<Emp> queryEmpByCondition3(Emp emp);

    /**
     * choose、when、otherwise
     */
    List<Emp> getEmpByChoose(Emp emp);

    /**
     * 通过数组实现批量删除
     */
    int removeMoreByArray(@Param("eids") Integer[] eids);

    int removeMoreByArray2(@Param("eids") Integer[] eids);

    //通过集合实现批量查询，参见SelectMapper中的queryUsersByUsernames

    /**
     * 批量增加，通过集合
     */
    void insertMoreByList(@Param("emps") List<Emp> list);

}
