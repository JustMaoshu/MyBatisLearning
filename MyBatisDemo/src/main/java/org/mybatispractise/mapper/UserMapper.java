package org.mybatispractise.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatispractise.pojo.User;
import org.mybatispractise.pojo.UserBo;

import java.util.List;
import java.util.Map;

public interface UserMapper {

     /*
     * MyBatis面向接口编程的两个一致:
     * 1、映射文件的namespace要和mapper接口的全类名保持一致；
     * 全类名：代码主目录java后的路径+类名
     * 2、映射文件中SQL语句的id要和mapper接口中的方法名一致。
     */

    void insertUser();

    void insertUser2(User user);

    void deleteUser();

    void updateUser();

    List<User> queryAllUsers();

    User queryUserByUsername(String username);

    User queryUserByPasswordAndAge(String password,int age);

    User checkLogin(String username,String password);

    User checkLoginByMap(Map<String,Object> map);

    User checkLoginByParam(@Param("testUsername") String username, @Param("password") String password);

    void insertUserByBo(UserBo userBo);

}
