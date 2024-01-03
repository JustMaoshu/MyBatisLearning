package com.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatispractise.mapper.CacheMapper;
import org.mybatispractise.pojo.Emp;
import org.mybatispractise.utils.SqlSessionUtil;

import java.io.IOException;
import java.io.InputStream;

public class CacheTest {

    @Test
    public void testOneCache() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession sqlSession = sqlSessionUtil.getSqlSession();
        CacheMapper mapper = sqlSession.getMapper(CacheMapper.class);
        Emp emp = mapper.queryEmpByEid(1);
        System.out.println(emp);
        //一级缓存生效，不重复执行该sql
        Emp emp2 = mapper.queryEmpByEid(1);
        System.out.println(emp2);
        //手动清空缓存，之后的sql就要重新执行查询
        sqlSession.clearCache();
        Emp emp3 = mapper.queryEmpByEid(1);
        System.out.println(emp3);
    }

    @Test
    public void testTwoCache() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession session1 = sessionFactory.openSession(true);
        CacheMapper cacheMapper1 = session1.getMapper(CacheMapper.class);
        System.out.println(cacheMapper1.queryEmpByEid(1));
        //clearCache不影响数据进入二级缓存，但仅使用clearCache也不会让二级缓存生效，需要close或commit才行
        session1.clearCache();
        //二级缓存必须在关闭或提交一级缓存之后才能生效，一级缓存的数据在提交或关闭时存入二级缓存
        session1.close();
        SqlSession session2 = sessionFactory.openSession(true);
        CacheMapper cacheMapper2 = session2.getMapper(CacheMapper.class);
        //两次查询之间不能进行任何增删改，否则一级缓存和二级缓存同时失效
        System.out.println(cacheMapper2.queryEmpByEid(1));
        session2.close();
    }

}
