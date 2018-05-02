package com.inovatrend.carService.dao;

import com.inovatrend.carService.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByCarId(Long id);


    @Transactional
    void deleteByCarId(Long id);



}
