package com.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Rule;
import org.junit.Test;
import org.mybatispractise.mapper.DynamicSQLMapper;
import org.mybatispractise.pojo.Emp;
import org.mybatispractise.utils.SqlSessionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicSQLTest {

    /**
     * 动态sql：用于根据特定条件动态拼装sql语句
     * 1、if：根据标签中test属性对应的表达式决定标签中内容是否拼接到sql中，if成立才拼接
     * 2、where：当where标签中有内容时，会自动生成where关键字，并且将内容前多余的and或or去掉
     * 当where中没有内容时，此时where标签没有任何效果，并不会有多余的where关键字
     * <where>只能去掉内容前多余的and或or，不能将内容后多余的and或or去掉，也不能增加缺省的and或or，所以and、or要注意写法
     * 3、trim：
     * 若标签中有内容：
     * prefix|suffix：将标签中内容前或内容后添加指定内容
     * prefixOverrides|suffixOverrides：将标签中内容前或内容后的指定内容去掉
     * 若标签中没有内容，则trim标签没有任何效果
     * 4、choose、when、otherwise，相当于if、else if、else
     * when至少要有一个（相当于最开始的if），otherwise之多只有一个
     * choose、when、otherwise相当于if、else if、else，是判断，只要一个成立则之后的都不再判断，不可以加and或or
     * 5、foreach
     * collection:设置需要循环的数组或集合
     * item:表示数组或集合中的每一个元素
     * separator:循环体之间的分隔符
     * open:循环前的开始符
     * close:循环结束的结束符
     * 6、sql
     * 记录常用的sql片段
     * 例如设置sql片段：<sql id="empColumns">eid,emp_name,age,sex,email</sql>，这样就不用重复写了
     * 引用sql片段：<include refid="empColumns"></include>
     */


    @Test
    public void testGetEmpByCondition() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession session = sqlSessionUtil.getSqlSession();
        DynamicSQLMapper mapper = session.getMapper(DynamicSQLMapper.class);

        List<Emp> empList = mapper.queryEmpByCondition(new Emp(null, "李四", null, '男', null, null, null));
        System.out.println(empList);

        List<Emp> empList1 = mapper.queryEmpByCondition2(new Emp(null, null, 48, '女', null, null, null));
        System.out.println(empList1);

        List<Emp> empList2 = mapper.queryEmpByCondition3(new Emp(null, null, 48, '女', null, null, null));
        System.out.println(empList2);
    }

    @Test
    public void testGetEmpByChoose() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession session = sqlSessionUtil.getSqlSession();
        DynamicSQLMapper mapper = session.getMapper(DynamicSQLMapper.class);
        List<Emp> list = mapper.getEmpByChoose(new Emp(null, null, null, null, null, null, null));
        System.out.println(list);
    }

    @Test
    public void testForEach() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession session = sqlSessionUtil.getSqlSession();
        DynamicSQLMapper mapper = session.getMapper(DynamicSQLMapper.class);
//        int res = mapper.removeMoreByArray(new Integer[]{6, 7, 8});
//        System.out.println(res);

//        mapper.removeMoreByArray2(new Integer[]{6,7,8});
        Emp emp1 = new Emp(null,"a",23,'男',"123@qq.com",null,null);
        Emp emp2 = new Emp(null,"b",23,'男',"123@qq.com",null,null);
        Emp emp3 = new Emp(null,"c",23,'男',"123@qq.com",null,null);
        List<Emp> list = Arrays.asList(emp1, emp2, emp3);
        mapper.insertMoreByList(list);


    }

}
