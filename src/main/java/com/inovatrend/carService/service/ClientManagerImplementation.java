package com.inovatrend.carService.service;

import com.inovatrend.carService.dao.ClientRepository;
import com.inovatrend.carService.domain.Client;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientManagerImplementation implements ClientManager {

    private final ClientRepository clientRepository;

    public ClientManagerImplementation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        Sort sort = new Sort(Sort.Direction.ASC, "lastName");
        return clientRepository.findAll(sort);
    }

    @Override
    public Optional<Client> getClient(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
