package ru.ktsybenkov.tgBot.controller;

import org.springframework.web.bind.annotation.*;
import ru.ktsybenkov.tgBot.entity.Client;
import ru.ktsybenkov.tgBot.entity.ClientOrder;
import ru.ktsybenkov.tgBot.entity.Product;
import ru.ktsybenkov.tgBot.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/rest/clients/")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable(name = "id") Long clientId){
        return clientService.getClientOrders(clientId);
    }

    @GetMapping("/{id}/products")
    public List<Product> getClientOrderedProducts(@PathVariable(name = "id") Long clientId){
        return clientService.getClientsOrderedProducts(clientId);
    }

    @GetMapping(value = "search",
            produces = "application/json; charset=UTF-8")
    public List<Client> partialNameSearch(
            @RequestParam(name = "name") String partialName
    ){
        return clientService.partialNameSearch(partialName);
    }
}
