package ru.ktsybenkov.tgBot.controller;

import org.springframework.web.bind.annotation.*;
import ru.ktsybenkov.tgBot.entity.Product;
import ru.ktsybenkov.tgBot.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/rest/products/")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "search", produces = "application/json; charset=UTF-8")
    public List<Product> getProductsOfCategory(
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "name", required = false) String partialName
    ){
        if(categoryId != null)
            return productService.getProductsOfCategory(categoryId);
        else
            return productService.partialNameSearch(partialName);
    }

    @GetMapping("popular")
    public List<Product> getMostPopularProducts(@RequestParam(name = "limit") Long topLimit){
        return productService.getMostPopularProducts(topLimit);
    }
}
