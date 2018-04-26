package com.inovatrend.carService.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_sequence")
    @SequenceGenerator(name = "service_sequence", allocationSize = 10)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "service_date")
    private String serviceDate;

    @Column(name = "service_time")
    private String serviceTime;

    @Column(name = "worker_first_name")
    private String workerFirstName;

    @Column(name = "worker_last_name")
    private String workerLastName;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "service_price")
    private String servicePrice;

    @Column(name = "paid")
    private boolean paid;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
