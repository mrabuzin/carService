package com.inovatrend.carService.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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

    @Column(name = "service_time")
    private Date serviceTime = new Date();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(serviceTime, service.serviceTime) &&
                Objects.equals(car, service.car);
    }

    @Override
    public int hashCode() {

        return Objects.hash(serviceTime, car);
    }
}
