package ru.ktsybenkov.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ktsybenkov.tgBot.dao.OrderProductRepository;
import ru.ktsybenkov.tgBot.dao.ProductRepository;
import ru.ktsybenkov.tgBot.entity.OrderProduct;
import ru.ktsybenkov.tgBot.entity.Product;
import ru.ktsybenkov.tgBot.record.ProductCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<Product> getProductsOfCategory(Long categoryId){
        return productRepository.findAll().stream()
                .filter(
                        product -> product.getCategory().getId().equals(categoryId)
                )
                .collect(Collectors.toList());
    }

    public List<Product> getMostPopularProducts(Long topLimit){
        Map<Product, ProductCount> productCounts = new HashMap<>();

        for(OrderProduct orderProduct : orderProductRepository.findAll()){
            ProductCount productCount = productCounts.get(orderProduct.getProduct());
            if(productCount != null){
                productCount.increaseCount(orderProduct.getCountProduct());
            }
            else{
                productCounts.put(orderProduct.getProduct(), new ProductCount(orderProduct.getProduct(),
                        orderProduct.getCountProduct()));
            }
        }


        return productCounts.values().stream()
                .sorted((count1, count2) ->
                        Integer.compare(
                                count2.getCount(),
                                count1.getCount()
                        )
                ).limit(topLimit)

                .map(ProductCount::getProduct)

                .collect(Collectors.toList());

    }

    public List<Product> partialNameSearch(String partialName){
        return productRepository.findAll().stream()
                .filter(
                        product -> product.getName().contains(partialName)
                )
                .collect(Collectors.toList());
    }
}
