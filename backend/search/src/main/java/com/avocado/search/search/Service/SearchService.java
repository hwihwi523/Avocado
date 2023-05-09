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

    private boolean checkCategory(String category){


        return false;
    }


    public List<Product> searchProduct(String category, String keyword){

        SearchResponse<Product> search;
        List<Product> products = new ArrayList<>();

        //조건
        Query byName = MatchQuery.of(m -> m
                .field("name")
                .query(keyword)
        )._toQuery();
        Query byCategory = MatchQuery.of(r -> r
                .field("category_eng")
                .query(category)
        )._toQuery();

        try {
            if(category.equals("All")){
                search = elasticsearchClient.search(s -> s
                                .index("products")
                                .query(q -> q
                                        .bool(b -> b
                                                .must(byName)
                                        )).size(50),
                        Product.class);
            }else {
                search = elasticsearchClient.search(s -> s
                                .index("products")
                                .query(q -> q
                                        .bool(b -> b
                                                .must(byName)
                                                .must(byCategory)
                                        )).size(50),
                        Product.class);
            }
            for (Hit<Product> hit: search.hits().hits()) {
                products.add(hit.source());
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return products;
    }
}
