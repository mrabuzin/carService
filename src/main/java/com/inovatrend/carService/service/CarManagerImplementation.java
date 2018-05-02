package com.inovatrend.carService.service;

import com.inovatrend.carService.dao.CarRepository;
import com.inovatrend.carService.domain.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerImplementation implements CarManager {

    private final CarRepository carRepository;
    private final  ServiceManager serviceManager;

    public CarManagerImplementation(CarRepository carRepository, ServiceManager serviceManager) {
        this.carRepository = carRepository;
        this.serviceManager = serviceManager;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void deleteCar(long id) {

        serviceManager.deleteByCarId(id);
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> findByClientId(Long clientId) {
        return carRepository.findByClientId(clientId);
    }



    @Override
    public Optional<Car> getCar(Long id) {
        return carRepository.findById(id);
    }
}
