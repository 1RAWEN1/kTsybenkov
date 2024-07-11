package ru.ktsybenkov.tgBot.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ktsybenkov.tgBot.entity.ClientOrder;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    List<ClientOrder> findByClientId(Long clientId);

    @Query("select co " +
            "from ClientOrder co " +
            "where co.client.id = :clientId and co.status = 1")
    ClientOrder findNewOrderByClientId(Long clientId);
}
