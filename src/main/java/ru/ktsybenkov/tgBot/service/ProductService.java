package ru.ktsybenkov.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ktsybenkov.tgBot.dao.OrderProductRepository;
import ru.ktsybenkov.tgBot.dao.ProductRepository;
import ru.ktsybenkov.tgBot.entity.Product;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<Product> getProductsOfCategory(Long categoryId){
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getMostPopularProducts(Long topLimit){
        return productRepository.findMostPopularProducts(topLimit);
    }

    public List<Product> search(String searchNameString){
        return productRepository.search(searchNameString);
    }

    public List<Product> search(String searchNameString, Long categoryId){
        if(categoryId != null)
            return getProductsOfCategory(categoryId);
        else
            return search(searchNameString);
    }
}
