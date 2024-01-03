package org.mybatispractise.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatispractise.pojo.Student;
import org.mybatispractise.pojo.User;

import java.util.List;

public interface SpecialSQLMapper {

    List<User> queryUserByLike(@Param("username") String username);

    List<User> queryTest(@Param("age") int age);

    int deleteMore(@Param("usernames") String usernames);

    void insertStudentByGeneratedKey(Student student);

}
