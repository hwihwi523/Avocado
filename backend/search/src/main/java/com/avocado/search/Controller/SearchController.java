package com.avocado.search.Controller;

import com.avocado.search.Dto.response.KeywordRespDto;
import com.avocado.search.Dto.response.ProductRespDto;
import com.avocado.search.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {

    private SearchService searchService;

    @Autowired
    SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductRespDto>> search(@RequestParam(name = "category", defaultValue = "All")String category, @RequestParam(name = "keyword", defaultValue = " ")String keyword){
        return new ResponseEntity<List<ProductRespDto>>(searchService.searchProduct(category,keyword), HttpStatus.OK);
    }

    @GetMapping("/recommends")
    public ResponseEntity<List<KeywordRespDto>> recommandKeyword(@RequestParam(name = "category", defaultValue = "All")String category, @RequestParam(name = "keyword", defaultValue = " ")String keyword){
        return new ResponseEntity<List<KeywordRespDto>>(searchService.searchKeyword(category,keyword), HttpStatus.OK);
    }

    @GetMapping("/modify/review")
    public ResponseEntity<String> modifyProductReview(){
        searchService.modifyProductReview();
        return new ResponseEntity<String>("success",HttpStatus.OK);
    }


}
