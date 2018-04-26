package com.inovatrend.carService.service;

import com.inovatrend.carService.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarManager {

    Car save(Car car);
    List<Car> getAllCars();
    void deleteCar(long id);
    List<Car> findByClientId(Long id);
    void deleteByClientId(Long id);
    Optional<Car> getCar(Long id);

}
