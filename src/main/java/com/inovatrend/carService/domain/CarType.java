package com.inovatrend.carService.domain;

import lombok.Getter;

@Getter
public enum CarType {

    YUGO("Yugo", "45"),
    AUDI("Audi", "A6"),
    TOYOTA("Toyota", "Prius"),
    RENAULT("Renault", "Megane"),
    BMW("BMW", "X6"),
    CITROEN("Citroen", "C4"),
    VOLKSWAGEN("VolksWagen", "Golf 5"),
    OPEL("Opel", "Astra"),
    PEUGEOT("Peugeot", "308"),
    KIA("Kia", "Pride"),
    FORD("Ford", "Focus");

    private String carManufacturer;
    private String model;

    CarType(String carManufacturer, String model) {

        this.carManufacturer = carManufacturer;
        this.model = model;
    }

    @Override
    public String toString() {
        return  carManufacturer + ' ' + model;
    }
}
