package ru.ktsybenkov.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ktsybenkov.tgBot.dao.ClientOrderRepository;
import ru.ktsybenkov.tgBot.dao.ClientRepository;
import ru.ktsybenkov.tgBot.dao.OrderProductRepository;
import ru.ktsybenkov.tgBot.entity.Client;
import ru.ktsybenkov.tgBot.entity.ClientOrder;
import ru.ktsybenkov.tgBot.entity.Product;

import java.util.List;

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
        return clientOrderRepository.findByClientId(clientId);
    }

    public List<Product> getClientsOrderedProducts(Long clientId){
        return orderProductRepository.findProductByClientOrderId(clientId);
    }

    public List<Client> search(String searchNameString){
        return clientRepository.search(searchNameString);
    }
}
