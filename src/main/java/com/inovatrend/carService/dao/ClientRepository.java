package com.inovatrend.carService.dao;

import com.inovatrend.carService.domain.Car;
import com.inovatrend.carService.domain.Client;
import com.inovatrend.carService.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {


}

