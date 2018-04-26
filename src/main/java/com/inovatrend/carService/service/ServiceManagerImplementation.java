package com.inovatrend.carService.service;

import com.inovatrend.carService.dao.ServiceRepository;
import com.inovatrend.carService.domain.Service;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceManagerImplementation implements  ServiceManager{

    private final ServiceRepository serviceRepository;

    public ServiceManagerImplementation(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public void deleteService(long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<Service> findByCarId(Long id) {
        return serviceRepository.findByCarId(id);
    }

    @Override
    public void deleteByCarId(Long id) {

        serviceRepository.deleteByCarId(id);

    }

    @Override
    public Optional<Service> getService(Long id) {
        return serviceRepository.findById(id);
    }
}
