package ru.ktsybenkov.tgBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ktsybenkov.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p " +
            "where LOWER(p.name) like concat('%', LOWER(:searchNameString), '%')")
    List<Product> search(String searchNameString);

    List<Product> findByCategoryId(Long categoryId);

    @Query("select op.product, sum(op.countProduct) as c from OrderProduct op " +
            "group by op.product " +
            "order by c desc " +
            "limit :limit")
    List<Product> findMostPopularProducts(Long limit);
}
