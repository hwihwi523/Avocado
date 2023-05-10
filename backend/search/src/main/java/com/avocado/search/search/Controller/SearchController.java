package com.avocado.search.search.Controller;

import com.avocado.search.search.Entity.Keyword;
import com.avocado.search.search.Entity.Product;
import com.avocado.search.search.Service.SearchService;
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
    public ResponseEntity<List<Product>> search(@RequestParam(name = "category", defaultValue = "All")String category, @RequestParam(name = "keyword", defaultValue = " ")String keyword){
        return new ResponseEntity<List<Product>>(searchService.searchProduct(category,keyword), HttpStatus.OK);
    }

    @GetMapping("/recommands")
    public ResponseEntity<List<Keyword>> recommandKeyword(@RequestParam(name = "category", defaultValue = "All")String category, @RequestParam(name = "keyword", defaultValue = " ")String keyword){
        return new ResponseEntity<List<Keyword>>(searchService.searchKeyword(category,keyword), HttpStatus.OK);
    }
}
