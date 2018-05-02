package com.inovatrend.carService.service;

import com.inovatrend.carService.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ServiceManager {

    Service save(Service service);
    Page<Service> getAllServices(Pageable pageable);
    void deleteService(long id);
    List<Service> findByCarId(Long id);
    void deleteByCarId(Long id);
    Optional<Service> getService(Long id);

}
