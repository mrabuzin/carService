package com.inovatrend.carService.service;

import com.inovatrend.carService.dao.ClientRepository;
import com.inovatrend.carService.domain.Car;
import com.inovatrend.carService.domain.Client;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientManagerImplementation implements ClientManager {

    private final ClientRepository clientRepository;
    private final CarManager carManager;

    public ClientManagerImplementation(ClientRepository clientRepository, CarManager carManager) {
        this.clientRepository = clientRepository;
        this.carManager = carManager;
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
        List<Car> clientCars =  carManager.findByClientId(id);
        for (Car clientCar : clientCars) {
            carManager.deleteCar(clientCar.getId());
        }
        clientRepository.deleteById(id);
    }
}
