package com.inovatrend.carService.dao;

import com.inovatrend.carService.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByClientId(Long id);

}
