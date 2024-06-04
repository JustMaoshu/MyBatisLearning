package com.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatispractise.mapper.SelectMapper;
import org.mybatispractise.pojo.User;
import org.mybatispractise.utils.SqlSessionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectTest {

    /***
     * MyBatis根据需求返回不同类型的查询结果
     * 1、查询一个实体类对象
     *      若明确查询结果只有一条，可用实体类直接接收
     * 2、查询一个List集合
     *      若查询结果有多条，不能用实体类直接接收，要用List集合接收
     *      注意用List接收的时候，resultType=list列表中的泛型，而不是List
     * 3、查询单个数据（聚合函数）
     *      在MyBatis中，对于Java中常用的类型都设置了类型别名
     *      例如：java.lang.Integer-->int|integer
     *      例如：int-->_int|_integer
     *      例如：Map-->map,List-->list
     * 4、查询一条数据为map集合
     *      一条数据对应一个实体对象，映射到map则属性名为键，属性值为值
     *      较为常用，因为查询出的数据有时没有对应的实体类对象，此时就要用map来接收查询结果
     * 5、查询多条数据为map集合
     *      a>查询结果映射为List<Map<String,Object>>，每个map保存一行数据，resultType为map
     *      b>查询结果映射为Map<String,Object>，每个Object保存一行数据，其中键为表主键，值为实体类对象，resultType为map
     *      此时要设置主键对应的属性是哪个，主键对应的属性作为map的键，用@MapKey设置
     * 上述4、5主要适用于多表查询返回查询结果，没有对应实体类对象时就用map存储
     */
    @Test
    public void selectTest() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession sqlSession = sqlSessionUtil.getSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);

        //1、根据用户名查询用户
        System.out.println("1、");
        User user = mapper.queryUserByUsername("huangchen");
        System.out.println(user);
        System.out.println("-------------------------------------");
        //2、查询所有用户
        System.out.println("2、");
        List<User> userList = mapper.queryAllUsers();
        userList.forEach(System.out::println);
        System.out.println("-------------------------------------");
        //3、查询单个数据：表中数据条数
        System.out.println("3、");
        int count = mapper.queryCount();
        System.out.println(count);
        System.out.println("-------------------------------------");
        //4、查询单条数据为map
        System.out.println("4、");
        Map<String,Object> map = mapper.queryUserByUsernameToMap("lizi");
        map.forEach((k,v)-> System.out.println(k+":"+v));
        System.out.println("-------------------------------------");
        //5、查询多条数据为map
        System.out.println("5、");
        List<Map<String,Object>> list = mapper.queryAllUsersToMap();
        list.forEach(System.out::println);
        System.out.println("-------------------------------------");
        Map<String,Object> map2 = mapper.queryAllUsersToMap2();
        map2.forEach((k,v)-> System.out.println(k+":"+v));

    }

    @Test
    public void selectTest2() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession sqlSession = sqlSessionUtil.getSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);

        List<String> usernameList = new ArrayList<>();
//        usernameList.add("lizi");
//        usernameList.add("yuanzi");
        List<User> users = mapper.queryUsersByUsernames(usernameList);
        System.out.println(users);
    }
}
