package ru.ktsybenkov.tgBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ktsybenkov.tgBot.entity.OrderProduct;
import ru.ktsybenkov.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("select distinct p " +
            "from Product p, OrderProduct op, ClientOrder co " +
            "where p = op.product and co = op.clientOrder and co.client.id = :clientOrderId")
    List<Product> findProductByClientOrderId(Long clientOrderId);
}
