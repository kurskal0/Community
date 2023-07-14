package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit, @Param("orderMode") int orderMode);

    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    @Update("update discuss_post set comment_count = #{commentCount} where id = #{id}")
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Update("update discuss_post set type = #{type} where id = #{id}")
    int updateType(@Param("id") int id, @Param("type") int type);

    @Update("update discuss_post set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Update("update discuss_post set score = #{score} where id = #{id}")
    int updateScore(@Param("id") int id, @Param("score") double score);
}
