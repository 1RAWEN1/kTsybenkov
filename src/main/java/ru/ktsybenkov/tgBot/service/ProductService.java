package ru.ktsybenkov.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ktsybenkov.tgBot.dao.ProductRepository;
import ru.ktsybenkov.tgBot.entity.Product;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getMostPopularProducts(Long topLimit){
        return productRepository.findMostPopularProducts(topLimit);
    }

    public List<Product> search(String searchNameString, Long categoryId){
        if(categoryId != null)
            return productRepository.findByCategoryId(categoryId);
        else
            return productRepository.search(searchNameString);
    }
}
