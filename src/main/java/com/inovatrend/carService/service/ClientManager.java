package com.inovatrend.carService.service;

import com.inovatrend.carService.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientManager {

    Client save(Client client);

    List<Client> getAllClients();

    Optional<Client> getClient(Long id);

    void deleteClient(Long id);


}
