package com.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatispractise.mapper.UserMapper;
import org.mybatispractise.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {

    /**
     * SqlSession默认不自动提交事务
     * 可以使用SqlSessionFactory.openSession(true)设置自动提交事务
     */

    @Test
    public void testMyBatis() throws IOException {
        //加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        //获取SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //Ctrl+Alt+V    自动补全声明
        //工厂模式
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        //获取会话 SqlSession，参数是autoCommit是否自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //获取Mapper接口对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //调用insertUser方法后流程为，先通过namespace找到userMapper对应的配置文件，再通过id=方法名找到方法对应的sql语句最后执行
//        int result = userMapper.insertUser();
//        //手动提交事务，提交事务是必须的，否则sql语句不会生效
//        //sqlSession.commit();
//        //增删改的返回值都是受影响的行数，插入一条则应该返回1；增删改的返回值也可以是void
//        System.out.println(result);
//        userMapper.deleteUser();
//        userMapper.insertUser();
//        userMapper.updateUser();
//
//        //mapper接口全类名+方法名确定一条sql语句，等同于xml中namespace+id确定一条sql语句
        User user = userMapper.queryUserByUsername("lizi");
        System.out.println(user.toString());

//        List<User> userList = userMapper.queryAllUsers();
//        userList.forEach(System.out::println);

    }

}
