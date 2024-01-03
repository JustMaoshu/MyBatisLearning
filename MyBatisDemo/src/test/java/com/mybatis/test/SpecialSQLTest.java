package com.mybatis.test;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mybatispractise.mapper.SpecialSQLMapper;
import org.mybatispractise.pojo.Student;
import org.mybatispractise.pojo.User;
import org.mybatispractise.utils.SqlSessionUtil;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Slf4j
@Log4j
public class SpecialSQLTest {

    /***
     * 1、模糊查询
     * 模糊查询直接用'%#{username}%'会报错，原因是#{}是预编译（底层使用preparedStatement调用set方法来赋值），会在参数前后加
     * 单引号，这么用会语法错误
     * 方法是a>可以用'%${username}%'进行字符串拼接
     * b>用concat函数拼接引号、%和#{username}:
     * concat('%',#{},'%')，注意此处拼接时各个部分保证为字符串即可，结果为字符串自然带引号，#{}本身有单引号
     * 也可以concat('%','${}','%')
     * c>"%"#{username}"%"  sql中 "%"'变量'"%" 的方式是合法的
     * 2、批量删除
     * a>delete from table where id in(${})此处只能用$不能用#，因为#{}会自动加单引号
     * b>使用#{}的话要使用动态sql，传输list参数进行遍历
     * 3、动态设置表名
     * 如果表名为参数，那么应当使用${tableName}，因为表名不加引号
     * 4、添加功能获取自增的主键
     * 获取自增的主键，需要在添加时设置两个属性，一是useGeneratedKeys=true，二是keyProperty=“主键名”
     * useGeneratedKeys=true:设置当前sql使用了自增的主键
     * keyProperty=“主键名”:将自增的主键的值赋值到映射文件中参数的某个属性（给主键赋值）
     */

    @Test
    public void test() throws IOException {
        SqlSessionUtil sqlSessionUtil = new SqlSessionUtil();
        SqlSession sqlSession = sqlSessionUtil.getSqlSession();
        SpecialSQLMapper mapper = sqlSession.getMapper(SpecialSQLMapper.class);

        System.out.println("1、");
        List<User> list = mapper.queryUserByLike("a");
        System.out.println(list);
        list = mapper.queryTest(28);
        System.out.println(list);
        System.out.println("-------------------------------------");
        System.out.println("2、");
        int delete = mapper.deleteMore("huangchen','djioajddx','wangmazi");
        log.info("delete num="+delete);
        System.out.println("-------------------------------------");
        System.out.println("4、");
        Student student = new Student(null,"daZhuTou",3);
        mapper.insertStudentByGeneratedKey(student);
        System.out.println("-------------------------------------");
    }

}
