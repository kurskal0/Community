package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

//    @Select("select from comment where status = 0 and entity_type = #{entityType} and entity_id = #{entityId} order by create_time asc limit #{offset}, #{limit}")
    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId, @Param("offset") int offset, @Param("limit") int limit);

//    @Select("select count(id) from comment where status = 0 and entity_type = #{entityType} and entity_id = #{entityId}")
    int selectCountByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    @Insert("insert into comment(user_id, entity_type, entity_id, target_id, content, status, create_time)" +
            "values(#{userId}, #{entityType}, #{entityId}, #{targetId}, #{content}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertComment(Comment comment);

    @Select("select from comment where id = #{id}")
    Comment selectCommentById(@Param("id") int id);
}
