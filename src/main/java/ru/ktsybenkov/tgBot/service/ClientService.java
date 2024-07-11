package ru.ktsybenkov.tgBot.service;

import jakarta.transaction.Transactional;
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
    private final ClientOrderRepository clientOrderRepository;

    private final OrderProductRepository orderProductRepository;

    private final ClientRepository clientRepository;

    public ClientService(ClientOrderRepository clientOrderRepository, OrderProductRepository orderProductRepository
            , ClientRepository clientRepository) {
        this.clientOrderRepository = clientOrderRepository;
        this.orderProductRepository = orderProductRepository;
        this.clientRepository = clientRepository;
    }

    public List<ClientOrder> getClientOrders(Long clientId){
        return clientOrderRepository.findByClientId(clientId);
    }

    public List<Product> getClientsOrderedProducts(Long clientId){
        return orderProductRepository.findProductByClientId(clientId);
    }

    public List<Client> search(String searchNameString){
        return clientRepository.search(searchNameString);
    }

    public Client getClientByExternalId(Long id){
        return clientRepository.findByExternalId(id);
    }

    public void saveClient(Client client){
        clientRepository.save(client);
    }
}
