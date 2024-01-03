package com.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatispractise.mapper.UserMapper;
import org.mybatispractise.pojo.User;
import org.mybatispractise.pojo.UserBo;
import org.mybatispractise.utils.SqlSessionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetParameterTest {

    /**
     * Mybatis获取参数的各种情况：
     * 1、mapper接口参数为单个的字面量类型
     *      参数与名字无关，只考虑参数位置
     * 2、mapper接口参数为多个
     *      MyBatis将参数放在一个map集合中，以两种方式存储：以arg0、arg1...为键；以param1、param2...为键
     *      因此需要通过#{}、${}以键的方式访问值即可
     * 3、mapper接口参数为多个，用map传参
     *      可以手动将参数放入一个map中，用自己定义的键名，仍然通过#{}、${}以键的方式访问值
     * 4、mapper接口方法的参数类型是实体类类型☆☆☆☆☆（常用）
     *      以属性名访问参数值，仍然通过#{属性名}的方式
     *      属性名就是get、set方法中后续部分的小写，等同于属性名，但有时候属性不存在仅有get、set方法也可以访问
     * 5、使用@Param注解命名参数☆☆☆☆☆（常用）
     *      使用@Param命名参数的本质，就是将多个参数手动放入原本的参数map中，这样map中既有原来的param1和param2，
     *      又有新名称的键值对，除了arg没有外相当于2+3，用哪个都是可以的
     *      要学会用打断点的方式查看源码和理解源码，这比学会知识点更重要
     *      第二种方法要知道，但编程中不推荐使用，代码难以阅读；第三种方法不如第五种快捷，所以同类条件下只用第5种，包括第一种情况下
     *      所以归根结底，熟练运用4、5，掌握1、2、3；除非参数是实体对象，否则全部加@Param，包括数组、集合等都用@Param
     */

    @Test
    public void testGetParam() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession session = sqlSessionUtil.getSqlSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        //1、根据用户名查询用户
        User user = userMapper.queryUserByUsername("wangmazi");
        System.out.println("1、");
        System.out.println(user);
        System.out.println("-------------------------------------");
        //2、根据密码和年龄查询用户、根据用户名和密码验证用户
        User user2 = userMapper.queryUserByPasswordAndAge("lizichouzhu",28);
        System.out.println("2、");
        System.out.println(user2);
        user2 = userMapper.checkLogin("lizi","zhangsan");
        System.out.println(user2);
        System.out.println("-------------------------------------");
        //3、根据用户名和密码验证用户，使用map
        Map<String,Object> map = new HashMap<>();
        map.put("username","yuanzi");
        map.put("password","yuanzi666");
        User user3 = userMapper.checkLoginByMap(map);
        System.out.println("3、");
        System.out.println(user3);
        System.out.println("-------------------------------------");
        //4、插入用户
        System.out.println("4、");
        User insertUser = new User("zhangxu","zhanglili",27);
//        userMapper.insertUser2(insertUser);

        UserBo userBo = new UserBo("huangchen","123123");
//        userMapper.insertUserByBo(userBo);
        System.out.println("-------------------------------------");
        //5、使用注解命名参数，验证用户
        System.out.println("5、");
        User user5 = userMapper.checkLoginByParam("lizi","lizichouzhu");
        System.out.println(user5);
        System.out.println("-------------------------------------");
    }
}
