package com.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatispractise.mapper.DeptMapper;
import org.mybatispractise.mapper.EmpMapper;
import org.mybatispractise.pojo.Dept;
import org.mybatispractise.pojo.Emp;
import org.mybatispractise.utils.SqlSessionUtil;

import java.io.IOException;
import java.util.List;

public class ResultMapTest {

    /**
     * 字段名和属性名不一致，不会导致报错，但会导致不一致的属性获取不到值。解决方法有多个：
     * 1、给查询结果起别名，使之与属性名一致：select eid,emp_name as name,...
     * 2、全局配置<settings></settings>中有一项是mapUnderscoreToCamelCase=true，将下划线自动映射为驼峰
     * 3、通过resultMap设置自定义映射关系
     * resultMap:用来设置自定义映射关系。其中id为唯一标识，type指定映射关系中的实体类类型
     *      id:设置主键的映射关系
     *      result:设置普通字段的映射关系
     *          property:属性名，column:字段名
     * -------------------------------------------------------------------------------------------
     * 处理多对一的映射关系：（例如员工->部门）
     * a>通过级联属性赋值：<result property="dept.did" column="did"/>（不常用）
     * b>通过association标签，使用javaType指定“一”的类型
     * c>分步查询
     * b是通过复杂sql语句进行多表查询，将查询结果通过association标签与类对应起来，而c是通过简单sql语句分步查询，在association
     * 标签中配置类型与分步查询以及结果的对应。分步查询可以实现延迟加载，但使用前需要在核心配置文件中设置全局信息。
     * -------------------------------------------------------------------------------------------
     * 延迟加载：
     * lazyLoadingEnabled：延迟加载的全局开关，当开启时，所有关联对象都会延迟加载。关联对象是指分步查询的第二步、第三步等，默认false
     * aggressiveLazyLoading：当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载，默认false
     * -------------------------------------------------------------------------------------------
     * 处理一对多的映射关系：（例如部门->员工）
     * a>使用Collection标签，Collection内要用ofType指定“多”的类型
     * b>分步查询
     * 此时的分步查询与多对一类似，使用Collection标签，内select:设置分步查询sql的唯一标识（namespace.sqlId或mapper接口的全类名.方法名
     * column:设置分步查询的条件，fetchType:设置是否延迟加载
     * 分步查询的标签里不用再次指定映射关系，只需要指定对应的sql语句，由指定的sql语句自己负责指定映射关系
     */

    @Test
    public void test() throws IOException {
        SqlSession session = new SqlSessionUtil().getSqlSession();
        EmpMapper empMapper = session.getMapper(EmpMapper.class);
        DeptMapper deptMapper = session.getMapper(DeptMapper.class);

        List<Emp> list = empMapper.queryAllEmp();
        list.forEach(System.out::println);
    }

    /**
     * 多对一映射关系测试
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        SqlSession session = new SqlSessionUtil().getSqlSession();
        EmpMapper empMapper = session.getMapper(EmpMapper.class);
        DeptMapper deptMapper = session.getMapper(DeptMapper.class);

        Emp emp = empMapper.queryEmpAndDept(2);
        System.out.println(emp);
    }

    /**
     * 多对一分步查询
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        SqlSession session = new SqlSessionUtil().getSqlSession();
        EmpMapper empMapper = session.getMapper(EmpMapper.class);
        DeptMapper deptMapper = session.getMapper(DeptMapper.class);

        Emp emp = empMapper.queryEmpAndDeptByStep1(2);
        System.out.println(emp.getName());
    }

    /**
     * 一对多Collection标签
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        SqlSession session = new SqlSessionUtil().getSqlSession();
        EmpMapper empMapper = session.getMapper(EmpMapper.class);
        DeptMapper deptMapper = session.getMapper(DeptMapper.class);

        Dept dept = deptMapper.queryDeptAndEmp(1);
        System.out.println(dept);
    }

    /**
     * 一对多分步查询
     * @throws IOException
     */
    @Test
    public void test5() throws IOException {
        SqlSession session = new SqlSessionUtil().getSqlSession();
        EmpMapper empMapper = session.getMapper(EmpMapper.class);
        DeptMapper deptMapper = session.getMapper(DeptMapper.class);

        Dept dept = deptMapper.queryDeptAndEmpStep1(2);
        System.out.println(dept);
    }
}
