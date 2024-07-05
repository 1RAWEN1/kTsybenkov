package ru.ktsybenkov.tgBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ktsybenkov.tgBot.entity.OrderProduct;

@RepositoryRestResource(collectionResourceRel = "order_products"
        , path = "order_products")
public interface OrderProductRepository
        extends JpaRepository<OrderProduct, Long> {
}
