package com.inovatrend.carService.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class ClientAdress {

    @Column(name = "street")
    private String street;

    @Column(name = "street_number")
    private int streetNumber;

    @Column(name = "postal")
    private int postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

}
