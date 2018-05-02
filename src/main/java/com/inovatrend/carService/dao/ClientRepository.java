package com.inovatrend.carService.dao;

import com.inovatrend.carService.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {


}

