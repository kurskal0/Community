package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.nowcoder.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTest {

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testInsertOne(){
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(143));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(145));
//        elasticsearchRestTemplate.save();
    }

    // 插入多条数据
//    @Test
//    public void testInsertList() {
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(101, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(102, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(103, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(111, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(112, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(131, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(132, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(133, 0, 100));
//        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(134, 0, 100));
//    }

}
