package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    @Update("update user set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Update("update user set header_url = #{headerUrl} where id = #{id}")
    int updateHeader(@Param("id") int id, @Param("headerUrl") String headerUrl);

    @Update("update user set password = #{password} where id = #{id}")
    int updatePassword(@Param("id") int id, @Param("password") String password);
}
