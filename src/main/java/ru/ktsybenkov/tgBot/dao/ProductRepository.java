package ru.ktsybenkov.tgBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ktsybenkov.tgBot.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
