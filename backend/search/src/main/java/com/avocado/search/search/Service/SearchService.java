package com.avocado.search.search.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.avocado.search.search.Dto.request.InventoryDto;
import com.avocado.search.search.Dto.request.ReviewDto;
import com.avocado.search.search.Dto.response.KeywordRespDto;
import com.avocado.search.search.Dto.response.ProductRespDto;
import com.avocado.search.search.Entity.Keyword;
import com.avocado.search.search.Entity.Product;
import com.avocado.search.search.error.ErrorCode;
import com.avocado.search.search.error.SearchException;
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

    public List<ProductRespDto> searchProduct(String category, String keyword){
        checkData(category,keyword);

        SearchResponse<Product> search;
        List<ProductRespDto> products = new ArrayList<>();

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
                                        )).size(20),
                        Product.class);
            }else {
                search = elasticsearchClient.search(s -> s
                                .index("products")
                                .query(q -> q
                                        .bool(b -> b
                                                .must(byName)
                                                .must(byCategory)
                                        )).size(20),
                        Product.class);
            }
            for (Hit<Product> hit: search.hits().hits()) {
                products.add(hit.source().toDto());
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<KeywordRespDto> searchKeyword(String category, String keyword) {
        checkData(category,keyword);

        SearchResponse<Keyword> search;
        List<Keyword> keywordList = new ArrayList<>();
        List<KeywordRespDto> keywordRespDtoList = new ArrayList<>();

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
                                .index("keywords")
                                .query(q -> q
                                        .bool(b -> b
                                                .must(byName)
                                        )).size(5),
                        Keyword.class);
            }else {
                search = elasticsearchClient.search(s -> s
                                .index("keywords")
                                .query(q -> q
                                        .bool(b -> b
                                                .must(byName)
                                                .must(byCategory)
                                        )).size(5),
                        Keyword.class);
            }
            for (Hit<Keyword> hit: search.hits().hits()) {
                keywordList.add(hit.source());
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Keyword key : keywordList){
            keywordRespDtoList.add(key.toDto());
        }

        return keywordRespDtoList;
    }

    public void modifyProductReview(){
        
        // 카프카로 데이터 받아오면 데이터 교체할 것
        ReviewDto product = new ReviewDto();
        product.setId(1);
        product.setTotal_score(150);
        product.setReview_count(30);
        try {
            elasticsearchClient.update(u -> u
                            .index("products")
                            .id(String.valueOf(product.getId()))
                            .doc(product)
                    , ReviewDto.class);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void modifyProductInventory(){

        // 카프카로 데이터 받아오면 데이터 교체할 것
        InventoryDto product = new InventoryDto();
        product.setId(1);
        product.setInventory(150);
        try {
            elasticsearchClient.update(u -> u
                            .index("products")
                            .id(String.valueOf(product.getId()))
                            .doc(product)
                    , ReviewDto.class);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void checkData(String category, String keyword){
        if(category == null){
            throw new SearchException(ErrorCode.CATEGORY_NULL_EXCEPTION);
        }

        if(keyword == null){
            throw new SearchException(ErrorCode.KEYWORD_NULL_EXCEPTIOIN);
        }

        if(!(category.equals("Bottomwear")||category.equals("Dress")||category.equals("Footwear")||category.equals("Bags")||category.equals("Accessories")||category.equals("All"))){
            throw new SearchException(ErrorCode.INVALID_CATEGORY);
        }
    }

}
