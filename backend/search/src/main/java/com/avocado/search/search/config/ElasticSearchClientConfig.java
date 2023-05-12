package com.avocado.search.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClientConfig {
    private RestClient restClient;
    private ElasticsearchTransport transport;
    private ElasticsearchClient elasticsearchClient;

//    private String eIp = "acc46369d4d4c4626934bf2a21423d47-659575785.ap-northeast-2.elb.amazonaws.com";
    private String eIp = "localhost";
    private int ePort = 9200;

    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        if(elasticsearchClient == null) {
            restClient = RestClient.builder(
                    new HttpHost(eIp, ePort)).build();

            // Create the transport with a Jackson mapper
            transport = new RestClientTransport(
                    restClient, new JacksonJsonpMapper());

            // And create the API client
            elasticsearchClient = new ElasticsearchClient(transport);
        }
        return elasticsearchClient;
    }
}
