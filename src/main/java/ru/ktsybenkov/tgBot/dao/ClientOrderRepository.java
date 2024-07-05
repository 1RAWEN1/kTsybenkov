package ru.ktsybenkov.tgBot.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ktsybenkov.tgBot.entity.ClientOrder;

@RepositoryRestResource(collectionResourceRel = "client_orders"
        , path = "client_orders")
public interface ClientOrderRepository
        extends JpaRepository<ClientOrder, Long> {
}
