package com.inovatrend.carService.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_sequence")
    @SequenceGenerator(name = "car_sequence", allocationSize = 10)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "registration_table")
    private String registrationTable;

    @Column(name = "color")
    private String color;

    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "car_type")
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<Service> serviceList;
}
