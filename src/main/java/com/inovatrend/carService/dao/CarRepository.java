package com.inovatrend.carService.dao;

import com.inovatrend.carService.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByClientId(Long id);


    @Transactional
    void deleteByClientId(Long id);

}
