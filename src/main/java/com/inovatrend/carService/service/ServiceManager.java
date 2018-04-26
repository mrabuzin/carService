package com.inovatrend.carService.service;

import com.inovatrend.carService.domain.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceManager {

    Service save(Service service);
    List<Service> getAllServices();
    void deleteService(long id);
    List<Service> findByCarId(Long id);
    void deleteByCarId(Long id);
    Optional<Service> getService(Long id);

}
