package com.nowcoder.community.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig {

//    private String host;
//    private Integer port;

//    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient(){
//        ClientConfiguration configuration = ClientConfiguration.builder()
//                .connectedTo("127.0.0.1:9300")
//                .build();
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
//        RestHighLevelClient client = RestClients.create(configuration).rest();
//        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
//        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
