package ru.ktsybenkov.tgBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ktsybenkov.tgBot.entity.ClientOrder;
import ru.ktsybenkov.tgBot.entity.OrderProduct;
import ru.ktsybenkov.tgBot.entity.Product;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("select distinct product " +
            "from OrderProduct op " +
            "where clientOrder.client.id = :clientOrderId")
    List<Product> findProductByClientId(Long clientOrderId);

    @Query("select op " +
            "from OrderProduct op " +
            "where op.clientOrder.id = :clientOrderId and op.product.id = :productId")
    OrderProduct findProductByClientOrderId(Long clientOrderId, Long productId);

    @Query("select distinct product " +
            "from OrderProduct op " +
            "where clientOrder.id = :clientOrderId")
    List<Product> findProductByClientOrderId(Long clientOrderId);

    List<OrderProduct> findByClientOrder(ClientOrder clientOrder);
}
