package ru.ktsybenkov.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ktsybenkov.tgBot.dao.ClientOrderRepository;
import ru.ktsybenkov.tgBot.dao.ClientRepository;
import ru.ktsybenkov.tgBot.dao.OrderProductRepository;
import ru.ktsybenkov.tgBot.entity.Client;
import ru.ktsybenkov.tgBot.entity.ClientOrder;
import ru.ktsybenkov.tgBot.entity.OrderProduct;
import ru.ktsybenkov.tgBot.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {
    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientOrder> getClientOrders(Long clientId){
        return clientOrderRepository.findAll().stream()
                .filter(
                        clientOrder -> clientOrder.getClient().getId().equals(clientId)
                )
                .collect(Collectors.toList());
    }

    public List<Product> getClientsOrderedProducts(Long clientId){
        return orderProductRepository.findAll().stream()
                .filter(
                        order -> order.getClientOrder().getClient().getId().equals(clientId)
                )
                .map(OrderProduct::getProduct)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Client> partialNameSearch(String partialName){
        return clientRepository.findAll().stream()
                .filter(
                        client -> client.getFullName().contains(partialName)
                )
                .collect(Collectors.toList());
    }
}
