package org.mybatispractise.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.mybatispractise.pojo.User;

import java.util.List;
import java.util.Map;

public interface SelectMapper {

    User queryUserByUsername(@Param("username") String username);

    int queryCount();

    @MapKey("username")
    Map<String,Object> queryUserByUsernameToMap(@Param("username") String username);

    @MapKey("username")
    List<Map<String,Object>> queryAllUsersToMap();

    @MapKey("username")
    Map<String,Object> queryAllUsersToMap2();

    List<User> queryAllUsers();

    List<User> queryUsersByUsernames(@Param("usernames") List<String> usernames);

}
