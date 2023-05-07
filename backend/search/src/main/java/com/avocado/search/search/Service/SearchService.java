package com.avocado.search.search.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.avocado.search.search.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;


@Service
public class SearchService {
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    public SearchService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public List<Product> searchProduct(String type, String keyword){

        SearchResponse<Product> search;
        List<Product> products = new ArrayList<>();
        System.out.println(type);
        System.out.println(keyword);

        //조건
        Query byName = MatchQuery.of(m -> m
                .field("name")
                .query(keyword)
        )._toQuery();
        Query byCategory = MatchQuery.of(r -> r
                .field("category_eng")
                .query(type)
        )._toQuery();

        try {
            search = elasticsearchClient.search(s -> s
                            .index("products")
                            .query(q -> q
                                    .bool(b -> b
                                            .must(byName)
                                            .must(byCategory)
                                    )).size(50),
                    Product.class);
            for (Hit<Product> hit: search.hits().hits()) {
                products.add(hit.source());
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(products);
        return products;
    }
}
