package com.inovatrend.carService.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "clients")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", allocationSize = 10)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private ClientAdress clientAdress = new ClientAdress();

//    @Column(name = "car_list")
//    private List<Car> carList;

}
