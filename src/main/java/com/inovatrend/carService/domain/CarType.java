package com.inovatrend.carService.domain;

import lombok.Getter;

@Getter
public enum CarType {

    YUGO("Yugo"),
    AUDI("Audi"),
    TOYOTA("Toyota"),
    RENAULT("Renault"),
    BMW("BMW"),
    CITROEN("Citroen"),
    VOLKSWAGEN("VolksWagen"),
    OPEL("Opel"),
    PEUGEOT("Peugeot"),
    KIA("Kia"),
    FORD("Ford");

    private String label;

    CarType(String carType) {

        this.label = carType;

    }

    public String getLabel() {
        return label;
    }
}
