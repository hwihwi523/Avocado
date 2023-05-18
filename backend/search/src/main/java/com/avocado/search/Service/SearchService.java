package com.avocado.search.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.avocado.CompactReview;
import com.avocado.Merchandise;
import com.avocado.PurchaseHistory;
import com.avocado.search.Entity.Keyword;
import com.avocado.search.Entity.Product;
import com.avocado.search.Dto.request.InventoryDto;
import com.avocado.search.Dto.request.ReviewDto;
import com.avocado.search.Dto.response.KeywordRespDto;
import com.avocado.search.Dto.response.ProductRespDto;
import com.avocado.search.error.ErrorCode;
import com.avocado.search.error.SearchException;
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
        Query byCategoryKor = MatchQuery.of(r -> r
                .field("category_kor")
                .query(keyword)
        )._toQuery();

        try {
            if(keyword.equals("전체")){
                search = elasticsearchClient.search(s -> s
                                .index("products").size(20),
                        Product.class);
            }else if(category.equals("All")){
                search = elasticsearchClient.search(s -> s
                                .index("products")
                                .query(q -> q
                                        .bool(b -> b
                                                .should(byName)
                                                .should(byCategoryKor)
                                        )).size(20),
                        Product.class);
            }else {
                search = elasticsearchClient.search(s -> s
                                .index("products")
                                .query(q -> q
                                        .bool(b -> b
                                                .should(byName)
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
        Query byCategoryKor = MatchQuery.of(r -> r
                .field("category_kor")
                .query(keyword)
        )._toQuery();

        try {
            if(category.equals("All")){
                search = elasticsearchClient.search(s -> s
                                .index("keywords")
                                .query(q -> q
                                        .bool(b -> b
                                                .should(byName)
                                                .should(byCategoryKor)
                                        )).size(5),
                        Keyword.class);
            }else {
                search = elasticsearchClient.search(s -> s
                                .index("keywords")
                                .query(q -> q
                                        .bool(b -> b
                                                .should(byName)
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

    public void modifyProductReview(CompactReview compactReview){

        ReviewDto product = new ReviewDto();
        product.setId(compactReview.getMerchandiseId());
        product.setTotal_score(compactReview.getTotalScore());
        product.setReview_count(compactReview.getReviewCount());
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

    public void modifyProductInventory(PurchaseHistory purchaseHistory){


        for(Merchandise merchandise : purchaseHistory.getMerchandises()) {
            InventoryDto product = new InventoryDto();
            product.setId(merchandise.getMerchandiseId());
            product.setInventory(merchandise.getLeftover());
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
