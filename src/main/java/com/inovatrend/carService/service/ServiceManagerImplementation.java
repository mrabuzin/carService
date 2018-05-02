package com.inovatrend.carService.service;

import com.inovatrend.carService.dao.ServiceRepository;
import com.inovatrend.carService.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<Service> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable);
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
